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
                handleNewGame(parameters);
                return true;
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
            case "scatterspores":
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
            case "exit":
                return false;
            default:
                gm.getIOHandler().writeLine("Ismeretlen parancs: " + action);
                return true;
        }
    }

    /**
     * Kezeli a 'newgame' parancsot, amely új csapat(ok) létrehozását és hozzáadását végzi a játékhoz.
     *
     * Ha nem adnak meg paramétert, akkor interaktív módon olvas be csapatokat addig,
     * amíg a felhasználó be nem írja az "xxx" szót csapatnévként. Minden csapathoz
     * be kell kérni a gombász és a rovarász nevét is.
     *
     * Ha a "-t" paraméterrel hívják meg, akkor a paraméter utáni szám meghatározza, hány csapatot kell létrehozni.
     * Ebben az esetben az adott számú csapat kerül bekérésre.
     *
     * Hibás paraméter esetén hibaüzenetet ír ki.
     *
     * @param parameters A parancs argumentumai, pl. {"-t", "3"} több csapat létrehozásához.
     * @throws IOException Ha a bemenet/kiírás során hiba történik.
     */
    public void handleNewGame(String[] parameters) throws IOException {
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
                    return;
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
        }
        else {
            ioHandler.writeLine("HIBA: ismeretlen newgame paraméter");
        }
    }

    /**
     * Kezeli a 'pos-alloc' parancsot, amely a csapatok kezdőpozíciójának kiosztását végzi.
     *
     * Ha nem adnak meg paramétert, akkor a {@code GameManager.setStartingPosition()} metódus
     * segítségével véletlenszerűen kiosztja a kezdőpozíciókat minden csapatnak.
     *
     * Manuális kiosztás esetén, ha a paraméterek a következő formában szerepelnek:
     * {@code -m <csapatnév> <tektonID>}, akkor a megadott csapatnak beállítja a kezdőpozícióját
     * a megadott tektonra. A {@code Team.setPositions()} metódus mindkét paraméterként kapott
     * tektonra ugyanazt a tekton példányt állítja be.
     *
     * - Ha a csapat nem található a nevek alapján, hibaüzenetet ír ki.
     * - Ha a megadott tekton ID nem szerepel a játéktérben, szintén hibaüzenetet ír ki.
     *
     * @param parameters A parancs argumentumai, lehetnek üresek (random kiosztás) vagy manuális formátumúak.
     * @throws IOException Ha a be- vagy kimenet során hiba történik.
     */
    public void handlePosAlloc(String[] parameters) throws IOException {
        if (parameters.length == 0) { // random kiosztás
            gm.setStartingPosition();
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
                team.setPositions(tecton, tecton);
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
        if(parameters.length != 3 || List.of(parameters).contains(null)){
            ioHandler.writeLine("HIBA: Hiányzó paraméterek.\ngrowstring <fonál> <tekton1> <tekton2>");
            return;
        }
        Tecton startTecton = gm.getTectons().stream()
        .findFirst().filter(t -> t.getId().equals(parameters[1])).get();

        Tecton destTecton = 
            gm.getTectons().stream()
            .findFirst().filter(t -> t.getId().equals(parameters[2])).get();

        MushroomString s = startTecton.getStrings().stream()
        .findFirst().filter(st -> st.getId().equals(parameters[0])).get();

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
     * @param paramters A parancsnak átadott paraméterek tömbje, amelynek tartalmaznia kell a rovar és a tekton azonosítóját.
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleMove(String[] parameters) throws IOException {
        if(parameters.length != 2 || List.of(parameters).contains(null)){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés.\nmove <rovar> <tekton>");
            return;
        }
        Tecton dest = gm.getTectons().stream().findFirst().filter(t -> t.getId().equals(parameters[1])).get();
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
            ioHandler.writeLine("HIBA: Nem létezik rovar, ezzel az azonosítóval: " + parameters[0]);
            return;
        }

        insect.moveTo(dest);
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
     * @throws IOException Ha a be- vagy kimenet során hiba történik 
    */
    public void handleBranch(String[] parameters) throws IOException {
        if(parameters.length != 2 || List.of(parameters).contains(null)){
            ioHandler.writeLine("HIBA: Rossz felparaméterezés\nbranch <fonal> <tekton>");
            return;
        }

        try {
            Tecton t = 
            gm.getTectons().stream()
            .findFirst().filter(tec -> tec.getId().equals(parameters[1])).get();

            MushroomString s = t.getStrings().stream().findFirst().filter(st-> st.getId().equals(parameters[0])).get();

            s.branchOut(t);
        } catch (NoSuchElementException e) {
            ioHandler.writeLine("HIBA: A paraméterként átadott entitások valamelyike nem létezik.");
        }

    }

    public void handleGrowMushroomBody(String[] parameters) {
        // Implementáció később
    }

    public void handleEatSpore(String[] parameters) {
        // Implementáció később
    }

    public void handleSum(String[] parameters) {
        // Implementáció később
    }

    public void handleEatInsect(String[] parameters) {
        // Implementáció később
    }

    public void handleCut(String[] parameters) {
        // Implementáció később
    }

    public void handleBreakTecton(String[] parameters) {
        // Implementáció később
    }

    public void handleTime(String[] parameters) {
        // Implementáció később
    }
}
