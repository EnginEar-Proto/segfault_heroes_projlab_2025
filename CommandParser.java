import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A CommandParser osztály felelős a string alapú parancsok értelmezéséért és
 * a megfelelő kezelőmetódusok meghívásáért.
 */
public class CommandParser {

    private GameManager gm;
    private IOHandler ioHandler;

    private int currentTeamIndex = 0;
    private int currentTectonIndex = 0;
    private int currentInsectIndex = 0;
    private int currentMushroomBodyIndex = 0;
    private int currentMushroomStringIndex = 0;
    private int currentSporeIndex = 0;


    /**
     * Beállítja a GameManager példányt, amit később a parancsok feldolgozásánál használ.
     *
     * @param gm A játékot kezelő GameManager példány.
     */
    public void setGameManager(GameManager gm) {
        this.gm = gm;
        this.ioHandler = gm.getIOHandler();
    }

    /**
     * Feldolgozza a kapott parancsot, és meghívja a megfelelő handler függvényt.
     *
     * @param command A beolvasott parancs szövege.
     */
    public boolean executeCommand(String command) throws IOException {
        if (gm == null) {
            System.out.println("GameManager nem lett beállítva.");
            return false;
        }

        if (command == null || command.isEmpty()) {
            gm.getIOHandler().writeLine("Üres parancs.");
            return false;
        }

        String[] parts = command.split(" ");
        String action = parts[0].toLowerCase();

        List<String> paramsAsList = new ArrayList<>(Arrays.asList(parts));
        paramsAsList.removeFirst();
        String[] parameters = paramsAsList.toArray(new String[paramsAsList.size()]);

        switch (action) {
            case "newgame":
                return handleNewGame(parameters);
            case "pos-alloc":
                handlePosAlloc(parameters);
                return true;
            case "start":
                handleStart(parameters);
                return true;
            case "growstring":
                handleGrowString(parameters);
                return true;
            case "move":
                handleMove(parameters);
                return true;
            case "branch":
                handleBranch(parameters);
                return true;
            case "growmushroombody":
                handleGrowMushroomBody(parameters);
                return true;
            case "eatspore":
                handleEatSpore(parameters);
                return true;
            case "sum":
                handleSum(parameters);
                return true;
            case "eatinsect":
                handleEatInsect(parameters);
                return true;
            case "cut":
                handleCut(parameters);
                return true;
            case "breaktecton":
                handleBreakTecton(parameters);
                return true;
            case "time":
                handleTime(parameters);
                return true;
            case "scatterspore":
                handleScatterSpore(parameters);
                return true;
            case "exit":
                return false;
            default:
                gm.getIOHandler().writeLine("Ismeretlen parancs: " + action);
                return false;
        }
    }

    /**
     * Kezeli a 'newgame' parancsot, amely új csapat(ok) létrehozását és hozzáadását végzi a játékhoz.
     * <p>
     * Ha nem adnak meg paramétert, akkor interaktív módon olvas be csapatokat addig,
     * amíg a felhasználó be nem írja az "xxx" szót csapatnévként. Minden csapathoz
     * be kell kérni a gombász és a rovarász nevét is.
     * <p>
     * Ha a "-t" paraméterrel hívják meg, akkor a paraméter utáni szám meghatározza, hány csapatot kell létrehozni.
     * Ebben az esetben az adott számú csapat kerül bekérésre.
     * <p>
     * Hibás paraméter esetén hibaüzenetet ír ki.
     *
     * @param parameters A parancs argumentumai, pl. {"-t", "3"} több csapat létrehozásához.
     * @throws IOException Ha a bemenet/kiírás során hiba történik.
     */
    public boolean handleNewGame(String[] parameters) throws IOException {
        gm.resetTeams();
        gm.initializeMap();

        String teamName;
        String player1;
        String player2;

        if (parameters.length == 0) {
            while (true) {
                ioHandler.write("Csapatnév: ");
                teamName = ioHandler.readLine();
                if (teamName.equalsIgnoreCase("xxx"))
                    return true;
                ioHandler.write("Gombász: ");
                player1 = ioHandler.readLine();
                ioHandler.write("Rovarász: ");
                player2 = ioHandler.readLine();

                Team team = new Team(teamName, new Insecter(player2), new Mushroomer(player1));
                gm.addTeam(team);
            }
        }
        else if (parameters[0].equals("-t") && parameters.length == 2) {
            int count = Integer.parseInt(parameters[1]);
            while (count-- > 0) {
                ioHandler.write("Csapatnév: ");
                teamName = ioHandler.readLine();
                ioHandler.write("Gombász: ");
                player1 = ioHandler.readLine();
                ioHandler.write("Rovarász: ");
                player2 = ioHandler.readLine();

                Team team = new Team(teamName, new Insecter(player2), new Mushroomer(player1));
                gm.addTeam(team);
            }
            return true;
        }
        else {
            ioHandler.writeLine("HIBA: ismeretlen newgame paraméter");
            return false;
        }
    }

    /**
     * Kezeli a 'pos-alloc' parancsot, amely a csapatok kezdőpozíciójának kiosztását végzi.
     * <p>
     * Ha nem adnak meg paramétert, akkor a {@code GameManager.setStartingPosition()} metódus
     * segítségével véletlenszerűen kiosztja a kezdőpozíciókat minden csapatnak.
     * <p>
     * Manuális kiosztás esetén, ha a paraméterek a következő formában szerepelnek:
     * {@code -m <csapatnév> <tektonID>}, akkor a megadott csapatnak beállítja a kezdőpozícióját
     * a megadott tektonra. A {@code Team.setPositions()} metódus mindkét paraméterként kapott
     * tektonra ugyanazt a tekton példányt állítja be.
     * <p>
     * - Ha a csapat nem található a nevek alapján, hibaüzenetet ír ki.
     * - Ha a megadott tekton ID nem szerepel a játéktérben, szintén hibaüzenetet ír ki.
     *
     * @param parameters A parancs argumentumai, lehetnek üresek (random kiosztás) vagy manuális formátumúak.
     * @throws IOException Ha a be- vagy kimenet során hiba történik.
     */
    public void handlePosAlloc(String[] parameters) throws IOException {
        if (parameters.length == 0) { // random kiosztás
            int i = gm.setStartingPosition(currentInsectIndex, currentMushroomBodyIndex, currentSporeIndex);
            currentMushroomBodyIndex += i;
            currentSporeIndex += (i * 8);
            currentInsectIndex += i;
            gm.listTeams();
        }
        else if (parameters[0].equals("-m") && parameters.length == 3) { // manuális kiosztás, egy parancs egy csapathoz rendel egy tektont
            Team team = null;
            Tecton tecton = null;

            for (Team t : gm.getTeams()) {
                if (t.getName().equals(parameters[1])) {
                    team = t;
                }
            }
            if (team == null) {
                ioHandler.writeLine("HIBA: ismeretlen csapatnév.");
                return;
            }

            for (Tecton t : gm.getTectons()) {
                if (t.getId().equals(parameters[2])) {
                    tecton = t;
                }
            }
            if (tecton == null) {
                ioHandler.writeLine("HIBA: ismeretlen tekton ID.");
            }
            else {
                boolean occupied = false;
                for (Team t : gm.getTeams()) {
                    if (t.getStartTecton() != null && t.getStartTecton().getId().equals(tecton.getId())) {
                        occupied = true;
                        break;
                    }
                }
                if (occupied) {
                    ioHandler.writeLine("HIBA: A tekton már foglalt.");
                    return;
                }
                else {
                    team.getMushroomer().addMushroomBody(new MushroomBody("mbd" + currentMushroomBodyIndex++, tecton));
                    team.getInsecter().addInsect(new Insect("ins" + currentInsectIndex++, 0, Ability.NORMAL, team.getInsecter(), tecton));
                    team.setPositions(tecton, tecton);
                }
            }
            gm.listTeams();
        }
        else {
            ioHandler.writeLine("HIBA: ismeretlen pos-alloc paraméter");
            return;
        }
    }

    public void handleStart(String[] parameters) {

        gm.setGameStarted(true);
        gm.setLaps(1);
    }

    /**
     * Kezeli a growstring parancsot, amely segítségével tektonok között fonál növeszthető.
     * 
     * <p>
     * A parancs felparaméterezve a következőkép néz ki:
     * </p>
     * {@code growstring <fonál> <induló-tekton> <cél-tekton>}
     * <p>
     * Ahol a fonál az a létező gombafonál, amelyet növeszteni szeretnénk.
     * A induló-tekton az a tekton, amelyen már a gombafonál jelen van, és amelyről indítjuk a fonal növesztést.
     * A cél-tekton az a tekton, amelyre a növesztett fonalat rávezetjük.
     * </p>
     * @param parameters A parancsnak itt kerül átadásra a fonál, a kiinduló és cél tektonok.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleGrowString(String[] parameters) throws IOException {
        if(parameters.length < 2){
            ioHandler.writeLine("HIBA: Hiányzó paraméterek.\ngrowstring <fonál> <tekton1> <tekton2>");
            return;
        }

        String thrd = parameters[1];

        if(parameters.length == 3) {
            thrd = parameters[2];
        }

        Tecton startTecton = gm.getTectons().stream()
        .filter(t -> t.getId().equals(parameters[1])).findFirst().get();

        Tecton destTecton =
            gm.getTectons().stream()
            .filter(t -> t.getId().equals(parameters[2])).findFirst().get();


        MushroomString s = null;
        try {
             s = startTecton.getStrings().stream()
            .filter(st -> st.getId().equals(parameters[0])).findFirst().get();
        } catch (Exception e) {
            if(parameters[1].equals(parameters[2])){
                s = new MushroomString(parameters[0], startTecton.getMushroomBody(), List.of(startTecton), null);
            }
        }

        s.growTo(startTecton, destTecton);
    }

    /**
     * Kezeli a move parancsot, amellyel a gombafonalakkal összekötött tektonokon mozgathatók a rovarok.
     * 
     * <p>
     * A parancs felparaméterezve a következőkép néz ki:
     * </p> 
     * {@code move <rovar> <cél-tekton>}
     * <p>
     * Ahol az első paraméter az a rovar amelyet mozgatni szeretnénk.
     * A második paraméter pedig a tekton ahova a rovart irányítjuk.
     * </p>
     * @param parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a rovar és a tekton azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleMove(String[] parameters) throws IOException {
        if(parameters.length != 2){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés.\nmove <rovar> <tekton>");
            return;
        }
        Tecton dest = gm.getTectons().stream().filter(t -> t.getId().equals(parameters[1])).findFirst().get();
        Insect insect = null;

        
        for(int i = 0; i < gm.getTeams().length; i++){
            List<Insect> teamInsects = gm.getTeams()[i].getInsecter().getInsects();
            for (Insect teamInsect : teamInsects) {
                if (teamInsect.getId().equals(parameters[0])) {
                    insect = teamInsect;
                }
            }
        }
        
        if(insect == null){
            ioHandler.writeLine("HIBA: Nem létezik rovar, ezzel az azonosítóval: " + parameters[0]);
            return;
        }
        else if(insect.getInsecter().getName().equals(gm.getCurrentTeam().getInsecter().getName()))
            insect.moveTo(dest);
        else
            ioHandler.writeLine("HIBA: A kiválasztott rovar nem a " + gm.getCurrentTeam().getName() + " csapaté!");
    }

    /**
     * Kezeli a scatterspore parancsot, amellyel a paraméterként átadott gombatest spóraja szórható el.
     * <p>
     *     A parancs felparaméterezve a követező kép néz ki:
     * </p>
     * {@code scatterspore <gombatest> <cél-tekton> [-t <spóra-típus>]}
     *<p>
     *     Ahol az első paraméter a gombatest, amely a spóráját fogja elszórni.
     *     A második paraméter a tekton, ahová a spóra szóródni fog.
     *     Harmadik opcionális paraméter a -t kapcsolóval elérhető, és a spóra típusa adható meg vele.
     *</p>
     * @throws IOException Ha a be- vagy kimenet során hiba történik
     * @params parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a rovar és a tekton azonosítóját.
     */
    public void handleScatterSpore(String[] parameters) throws IOException {
        if(parameters.length < 2 || parameters.length > 4) {
            ioHandler.writeLine("HIBA: Hiányzó paraméterek\nscatterspore <gombatest> <tekton> [-t <típus>]");
            return;
        }else{
            MushroomBody mbd = null;

            for(Tecton t : gm.getTectons()){
                if(t.getMushroomBody() == null) continue;

                if(t.getMushroomBody().getID().equals(parameters[0])){
                    mbd = t.getMushroomBody();
                    break;
                }
            }

            if(mbd == null){
                ioHandler.writeLine("HIBA: Nem létezik ilyen azonosítóval gombatest.");
                return;
            }

            Tecton desTecton = gm.getTectons().stream().filter(t -> t.getId().equals(parameters[1])).findFirst().get();
            if(parameters.length == 4 && parameters[3].equals("-t")){
                Ability ab = Ability.valueOf(parameters[3]);
                mbd.scatter(desTecton, ab);

                return;
            }

            mbd.scatter(desTecton);
        }
    }

    /**
     * Kezeli a branch parancsot, amellyel a tektonokon húzodó gombafonalat lehet egy másik tektonra ágaztatni.
     * Ez a parancs abban tér a grow parancstól, hogy a meglévő gombafonál meghosszabbítása helyett egy új fonalat hoz létre az adott tektonon.
     * <p>
     * A parancs felparaméterezve a következőkép néz ki:
     * </p>
     * {@code branch <fonal> <cél-tekton>}
     * <p>
     * Ahol az első paraméter az a fonal amelyet ágaztatni szeretnénk.
     * A második paraméter pedig a tekton amin az új fonalat ágaztatjuk.
     * </p>
     * @param parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a fonal és a tekton azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleBranch(String[] parameters) throws IOException {
        if(parameters.length != 2){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés\nbranch <fonal> <tekton>");
            return;
        }

        try {
            Tecton t = 
            gm.getTectons().stream()
            .filter(tec -> tec.getId().equals(parameters[1])).findFirst().get();

            MushroomString s = t.getStrings().stream().filter(st-> st.getId().equals(parameters[0])).findFirst().get();

            s.branchOut(t);
        } catch (NoSuchElementException e) {
            ioHandler.writeLine("HIBA: A paraméterként átadott entitások valamelyike nem létezik.");
        }

    }

    /**
     * Kezeli a growmushroombody parancsot, amellyel új gombatest növeszthető a paraméterként átadott tektonon.
     * 
     * <p>
     * A parancs felparaméterezve a következőkép néz ki:
     * </p>
     * {@code growmushroombody <tekton>}
     * <p>
     * Ahol az első paraméter az a tekton, amelyre az új gombatestet növeszteni akarjuk.
     * </p>
     * @param parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a tekton azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleGrowMushroomBody(String[] parameters) throws IOException {
        if(parameters.length != 1 ){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés\ngrowmushroombody <tekton>");
            return;
        }

        Tecton t = gm.getTectons().stream()
        .filter(tec -> tec.getId().equals(parameters[0])).findFirst().get();

        if (t.growBody()) {
            ioHandler.writeLine("Új Gombatest a tektonon");
        }
        else {
            ioHandler.writeLine("Gombatest növesztés nem lehetséges ezen a tektonon.");
        }
    }

    /**
     * Kezeli az eatspore parancsot, amellyel a rovar elfogyasztja a paraméterként átadott spórát, és
     * annak a tápértéke a rovarhoz adódik. Valamint a rovar megkapja a spóra effektjét, ha annak volt bármilyen.
     * <p>
     * A parancs felparaméterezve a következőkép néz ki:
     * </p>
     * {@code eatspore <rovar> <spóra>}
     * <p>
     * Ahol az első paraméter az a rovar, amely el fogja fogyasztani a spórát.
     * A második paraméter az a spóra, amely elfogyasztásra kerül.
     * </p>
     * @param parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a rovar és a spóra azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleEatSpore(String[] parameters) throws IOException {
        if(parameters.length != 2 ){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés\nbranch <rovar> <spóra>");
            return;
        }

        Spore sp = null;
        Insect insect = null;

        for(int i = 0; i < gm.getTeams().length; i++){
            List<Insect> teamInsects = gm.getTeams()[i].getInsecter().getInsects();
            for(int j = 0; j < teamInsects.size(); j++){
                List<Insect> options = teamInsects.stream().
                filter(ins ->ins.getid()
                .equals(parameters[0])).toList();

                if(!(options.isEmpty())){
                    insect = options.get(0);
                    break;
                }
            }
        }

        for(int i = 0; i < gm.getTectons().size(); i++){
            List<Spore> spores = gm.getTectons().get(i).getSpores();

            for (Spore spore : spores) {
                if(spore.getId().equals(parameters[1])){
                    sp = spore;
                    break;
                }
            }
        }

        if(sp == null || insect == null){
            ioHandler.writeLine("HIBA: a paraméterként átadott entitások valamelyike nem létezik ilyen azonosítóval.");
            return;
        }

        insect.eat(sp);
    }

    /**
     * Kezeli a sum parancsot, ammellyel a paraméterként átadott entitás adatai kérdezhetőek le.
     * <p>
     * 
     * </p>
     * {@code sum <entitás>}
     * <p>
     * Ahol az első paraméter az az entitás amelynek, az adatait lekérdezzük.
     * </p>
     * @param parameters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell az entitás azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleSum(String[] parameters) throws IOException {

        if(parameters.length == 0){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés\nsum <entitás>");
            return;
        }
        switch (parameters[0].substring(0,3)) {
            case "tek":
                Tecton searched = null;
                for (Tecton t: gm.getTectons()) {
                    if (t.getId().equals(parameters[0])) {
                        searched = t;
                    }
                }
                if (searched != null) {
                    ioHandler.writeLine("id: " + searched.getId());
                    ioHandler.write("Spores: ");
                    for (Spore s : searched.getSpores()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                    ioHandler.write("Insects: ");
                    for (Insect s : searched.getInsects()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                    ioHandler.write("Neighbours: ");
                    for (Tecton s : searched.getNeighbours()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                    ioHandler.write("Strings: ");
                    for (MushroomString s : searched.getStrings()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                }
                else {
                    ioHandler.writeLine("HIBA: A tekton nem található.");
                }
                break;
            case "spo":
                Spore sp = null;
                for(int i = 0; i < gm.getTectons().size(); i++){
                    List<Spore> spores = gm.getTectons().get(i).getSpores();
                    if(!spores.isEmpty()){
                        for (Spore spore : spores) {
                            if(spore.getId().equals(parameters[1])){
                                sp = spore;
                                break;
                            }
                        }
                    }
                }

                if(sp == null){
                    ioHandler.writeLine("HIBA: A megadott azonosítóval nem létezik ilyen entitás.");
                    return;
                }else{
                    ioHandler.writeLine("id: " + sp.getId() + " Ability: " + sp.getAbility().toString() + "Substance level: " + sp.getSubstance());
                }

                ioHandler.writeLine("");
                break;
            case "ins":
                Insect insect = null;

                for(int i = 0; i < gm.getTeams().length; i++){
                    List<Insect> teamInsects = gm.getTeams()[i].getInsecter().getInsects();
                    for(int j = 0; j < teamInsects.size(); j++){
                        List<Insect> options = teamInsects.stream().
                        filter(ins ->ins.getid()
                        .equals(parameters[0])).toList();
        
                        if(!(options.isEmpty())){
                            insect = options.get(0);
                            break;
                        }
                    }
                }

                if(insect == null){
                    ioHandler.writeLine("HIBA: A megadott azonosítóval nem lézeik ilyen entitás.");
                    return;
                }else{
                    ioHandler.writeLine("id: " + insect.getId() + " Ability: " + insect.getAbility() + "Substance level: " + insect.getSubstance());
                }

                ioHandler.writeLine("");
                break;
            case "inr":

                Insecter inrSearched = List.of(gm.getTeams()).stream().filter(team -> team.getInsecter().getName().equals(parameters[0])).findFirst().get().getInsecter();
                if (inrSearched != null) {
                    ioHandler.writeLine("id: " + inrSearched.getName());
                    ioHandler.write("Insects: ");
                    for (Insect s : inrSearched.getInsects()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                }
                else {
                    ioHandler.writeLine("HIBA: A rovarász nem található.");
                }
                break;
            case "msr":
                Mushroomer msrSearched = List.of(gm.getTeams()).stream().filter(team -> team.getMushroomer().getName().equals(parameters[0])).findFirst().get().getMushroomer();
                if (msrSearched != null) {
                    ioHandler.writeLine("id: " + msrSearched.getName());
                    ioHandler.write("Mushroombodies: ");
                    for (MushroomBody s : msrSearched.getMushroomBodies()) {
                        ioHandler.write(s.getID() + " ");
                    }
                    ioHandler.writeLine("");
                }
                else {
                    ioHandler.writeLine("HIBA: A gombász nem található.");
                }
                break;
            case "mbd":
                MushroomBody mdbS = null;
                for (int i = 0; i < gm.getTectons().size(); i++) {
                    MushroomBody mb = gm.getTectons().get(i).getMushroomBody();
                    if(mb == null) continue;
                    if (mb.getID().equals(parameters[0])) {
                        mdbS = mb;
                        break;
                    }
                }
                if (mdbS != null) {
                    ioHandler.writeLine("id: " + mdbS.getID());
                    ioHandler.writeLine("Tecton: " + mdbS.getTecton().getId());
                    ioHandler.writeLine("Strings: ");
                    for (MushroomString s : mdbS.getStrings()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                    ioHandler.write("Spores: ");
                    for (Spore s : mdbS.getSpores()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                }
                else {
                    ioHandler.writeLine("HIBA: A gombatest nem található.");
                }
                break;
            case "str":
                MushroomString searchedms = null;
                for(Tecton t: gm.getTectons()){
                    for(MushroomString str : t.getStrings()){
                        if(str.getId().equals(parameters[0])){
                            searchedms = str;
                            break;
                        }
                    }
                }
                if (searchedms != null) {
                    ioHandler.writeLine("id: " + searchedms.getId());
                    ioHandler.writeLine("Length: " + searchedms.getLength());
                    ioHandler.write("Mushroombodies: ");
                    for (MushroomBody s : searchedms.getMushroomBodies()) {
                        if (s != null) ioHandler.write(s.getID() + " ");
                    }
                    ioHandler.writeLine("");
                    ioHandler.write("Tectons: ");
                    for (Tecton s : searchedms.getTectons()) {
                        ioHandler.write(s.getId() + " ");
                    }
                    ioHandler.writeLine("");
                }
                else {
                    ioHandler.writeLine("HIBA: A fonál nem található.");
                    return;
                }

                ioHandler.writeLine("");
                break;
            case "all":
                ioHandler.writeLine("Tektonok: ");
                for (Tecton t : gm.getTectons()) {
                    ioHandler.write(t.getId() + " ");
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Rovarászok: ");
                for (Team t : gm.getTeams()) {
                    ioHandler.write(t.getInsecter().getName() + " ");
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Gombászok: ");
                for (Team t : gm.getTeams()) {
                    ioHandler.write(t.getMushroomer().getName() + " ");
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Rovarok: ");
                for (Team t : gm.getTeams()) {
                    for (Insect i : t.getInsecter().getInsects()) {
                        ioHandler.write(i.getId() + " ");
                    }
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Gombafonalak: ");
                for (Tecton t : gm.getTectons()) {
                    for (MushroomString i : t.getStrings()) {
                        ioHandler.write(i.getId() + " ");
                    }
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Gombatestek: ");
                for (Tecton t : gm.getTectons()) {
                    if (t.getMushroomBody() != null) {
                        ioHandler.write(t.getMushroomBody().getID() + " ");
                    }
                }
                ioHandler.writeLine("");
                ioHandler.writeLine("Spórák: ");
                for (Tecton t : gm.getTectons()) {
                    for (Spore i : t.getSpores()) {
                        ioHandler.write(i.getId() + " ");
                    }
                }
                ioHandler.writeLine("");

                break;
            default:
                ioHandler.writeLine("HIBA: A megadott azonosítóval nem lézeik ilyen entitás.");
                break;
        }        
    }

    /**
     * Kezeli az 'eatInsect' parancsot, amely a bénított rovarok elfogyasztását hajtja végre egy adott tektonon.
     * <p>
     * A parancs két paramétert vár:
     * <ul>
     *     <li>Az első paraméter a fonál azonosítója (MushroomString ID).</li>
     *     <li>A második paraméter a tekton azonosítója (Tecton ID).</li>
     * </ul>
     *
     * Működés:
     * <ul>
     *     <li>Megkeresi a megadott tektont és a tektonon belül a megfelelő fonalat.</li>
     *     <li>Ha a fonál vagy a tekton egyike nem található, hibaüzenetet ír ki .</li>
     *     <li>Ha megtalálta, meghívja a fonál {@code eatParalyzedInsects} metódusát az adott tektonra.</li>
     *     <li>Ha nincs bénított rovar a tektonon, szintén figyelmeztető üzenetet ír ki.</li>
     * </ul>
     *
     * @param parameters A parancs argumentumai: fonál ID és tekton ID.
     * @throws IOException Ha bemeneti/kimeneti hiba történik.
     */
    public void handleEatInsect(String[] parameters) throws IOException {
        if (parameters.length == 2) {

            MushroomString mushroomString = null;
            Tecton tecton = null;

            for (Tecton t : gm.getTectons()) {
                if (t.getId().equals(parameters[1])) {
                    tecton = t;
                    break;
                }
            }

            if (tecton == null) {
                ioHandler.writeLine("HIBA: A megadott tekton nem található.");
                return;
            }

            // Fonál keresése tektonon belül
            for (MushroomString ms : tecton.getStrings()) {
                if (ms.getId().equals(parameters[0])) {
                    mushroomString = ms;
                    break;
                }
            }

            if (mushroomString == null) {
                ioHandler.writeLine("HIBA: A megadott fonál nem található a tektonon.");
                return;
            }

            boolean foundParalyzedInsect = mushroomString.eatParalyzedInsects(tecton);
            if (!foundParalyzedInsect) ioHandler.writeLine("A megadott tektonon nincs bénított rovar.");
        }
        else {
            ioHandler.writeLine("HIBA: Rossz paraméterezés.");
        }
    }

    /**
     * Kezeli a 'cut' parancsot, amely egy rovarral való fonál elvágását valósítja meg két tekton között.
     * <p>
     * A parancs négy paramétert vár:
     * <ul>
     *     <li>Az első paraméter a rovar azonosítója (Insect ID).</li>
     *     <li>A második paraméter a fonál azonosítója (MushroomString ID).</li>
     *     <li>A harmadik paraméter az egyik tekton azonosítója (Tecton ID) amelyik között az elvágás történik.</li>
     *     <li>A negyedik paraméter a másik tekton azonosítója (Tecton ID) amelyik között az elvágás történik.</li>
     * </ul>
     *
     * Működés:
     * <ul>
     *     <li>Megkeresi a megfelelő rovart, fonalat és tektonokat az ID-k alapján.</li>
     *     <li>Ha bármelyik entitás nem található, megfelelő hibaüzenetet ír ki.</li>
     *     <li>Ha mindegyik megtalálható, meghívja a rovar {@code sabotageString} metódusát a megadott paraméterekkel.</li>
     *     <li>Ha a két tekton nem szomszédos, hibaüzenetet ír ki.</li>
     * </ul>
     *
     * @param parameters A parancs argumentumai: rovar ID, fonál ID, kezdő tekton ID, cél tekton ID.
     * @throws IOException Ha bemeneti/kimeneti hiba történik.
     */
    public void handleCut(String[] parameters) throws IOException {
        if (parameters.length == 4) {
            Insect insect = null;
            MushroomString mushroomString = null;
            Tecton tecton1 = null;
            Tecton tecton2 = null;
            for (Tecton t : gm.getTectons()) {
                for (Insect i : t.getInsects()) {
                    if (i.getId().equals(parameters[0])) {
                        insect = i;
                        for (MushroomString ms : t.getStrings()) {
                            if (ms.getId().equals(parameters[1])) {
                                mushroomString = ms;
                            }
                        }
                    }
                }
                if (t.getId().equals(parameters[2])) {
                    tecton1 = t;
                }
                if (t.getId().equals(parameters[3])) {
                    tecton2 = t;
                }
            }
            if (insect == null) {
                ioHandler.writeLine("HIBA: Nem található rovar ilyen id-val.");
            }
            if (mushroomString == null) {
                ioHandler.writeLine("HIBA: Nem található fonál ilyen id-val.");
            }
            if (tecton1 == null) {
                ioHandler.writeLine("HIBA: Nem található kezdő tekton ilyen id-val.");
            }
            if (tecton2 == null) {
                ioHandler.writeLine("HIBA: Nem található végső tekton ilyen id-val.");
            }
            if (insect != null && mushroomString != null && tecton1 != null && tecton2 != null) {
                int res = insect.sabotageString(mushroomString, tecton1, tecton2);
                if (res == -1) ioHandler.writeLine("HIBA: A tektonok nem szomszédosak.");
                else ioHandler.writeLine("Rovar elvágta a fonalat");
            }
        }
        else {
            ioHandler.writeLine("HIBA: Rossz paraméterezés.");
        }
    }

    /**
     * Kezeli a 'breakTecton' parancsot, amely egy megadott tekton törését hajtja végre.
     * <p>
     * A parancs két paramétert vár:
     * <ul>
     *   <li>Az első paraméter a törendő tekton azonosítója (ID).</li>
     *   <li>A második paraméter egy egész szám, amely meghatározza a tekton új méretét.</li>
     * </ul>
     * </p>
     * Működés:
     * <ul>
     *   <li>Megkeresi az ID alapján a megfelelő tektont.</li>
     *   <li>Ha megtalálja, meghívja a {@code Break(int)} metódust a megadott paraméterrel.</li>
     *   <li>Ha a második paraméter nem egész szám, vagy ha a tekton nem található, hibaüzenetet ír ki.</li>
     *   <li>Hibás paraméterszám esetén is hibaüzenetet ad.</li>
     * </ul>
     *
     * @param parameters A parancs argumentumai: tekton azonosító és törési méret.
     * @throws IOException Ha bemeneti/kimeneti hiba történik.
     */
    public void handleBreakTecton(String[] parameters) throws IOException {
        if (parameters.length == 2) {
            int breakInt;
            try {
                breakInt = Integer.parseInt(parameters[1]); // 2. paraméter átalakítás int-re
            } catch (NumberFormatException e) {
                ioHandler.writeLine("HIBA: A második paraméter nem egész szám.");
                return;
            }

            boolean found = false;
            Tecton newTecton = null;
            for (Tecton t : gm.getTectons()) {
                if (t.getId().equals(parameters[0])) {
                    found = true;
                    newTecton = t.Break(breakInt);
                }
            }
            if (!found) {
                ioHandler.writeLine("HIBA: A tekton nem található.");
            }
            if (newTecton != null) gm.addTecton(newTecton);
            else {
                ioHandler.writeLine("HIBA: A tekton nem törhető el.");
            }
        }
        else {
            ioHandler.writeLine("HIBA: Rossz paraméterezés.");
        }
    }

    public void handleTime(String[] parameters) throws IOException {
        if (parameters.length == 0) {
            if (gm.getTeams()[gm.getTeams().length - 1].getName().equals(gm.getCurrentTeam().getName())) {
                gm.incrementLap();
                ioHandler.writeLine("Lap: " + gm.getLaps());
            }
            else {
                ioHandler.writeLine("HIBA: Még nem lépett minden csapat.");
            }
        }
        else if (parameters.length == 1 && parameters[0].equals("-t")) {
            gm.incrementLap();
        }
        else {
            ioHandler.writeLine("HIBA: Rossz time paraméterezés.");
        }
    }

}
