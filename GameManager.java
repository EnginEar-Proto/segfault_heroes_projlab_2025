import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * A GameManager osztály felelős a játék teljes lefolyásáért.
 * Kezeli a körök számát, a játék végét, a csapatok lépéseit és a tektonok állapotát.
 */
public class GameManager {
    private ArrayList<Tecton> tectons;
    private ArrayList<Team> teams;
    private int laps = 1;
    private IOHandler ioHandler;
    private CommandParser commandParser;
    private Team currentTeam;

    /**
     * Létrehozza a GameManager példányát a szükséges handler és parser példányokkal.
     *
     * @param ioHandler    A bemenetet olvasó osztály példánya.
     * @param commandParser   A parancsokat feldolgozó osztály példánya.
     */
    public GameManager(IOHandler ioHandler, CommandParser commandParser) {
        this.ioHandler = ioHandler;
        this.commandParser = commandParser;
        this.commandParser.setGameManager(this);
        this.tectons = new ArrayList<>();
        this.teams = new ArrayList<>();
    }

    /**
     * Törli az aktuálisan játszó csapatokat.
     */
    public void resetTeams() {
        teams.clear();
    }
    /**
     * Visszaadja az aktuálisan soron lévő csapatot.
     *
     * @return Az aktuális csapat.
     */
    public Team getCurrentTeam() {
        return currentTeam;
    }

    /**
     * Visszaadja a beállított IOHandlert.
     *
     * @return az ioHandler objektum
     */
    public IOHandler getIOHandler() {
        return ioHandler;
    }

    /**
     * Visszaadja a játéktérben szereplő tektonokat.
     *
     * @return A tektonok listája.
     */
    public ArrayList<Tecton> getTectons() {
        return tectons;
    }

    /**
     * Növeli a körök számát eggyel.
     */
    public void incrementLap() {
        laps++;
    }

    /**
     * Létrehozza a játék kezdőpályáját.
     * Inicializálja a tektonokat előre definiált módon.
     */
    public void initializeMap() {
        tectons.add(new Tecton(1, true, 1, 1));
        // Itt még kéne pár tekton
    }

    /**
     * Beállítja a játékosok kezdőpozícióit.
     * Minden csapatnak kisorsol 2 tekton pozíciót.
     */
    public void setStartingPosition() throws IOException {
        if (tectons.size() * 2 < tectons.size()) {
            ioHandler.writeLine("A kezőpozíciók nem oszthatóak ki, nem elegendő a tektonok száma");
        }
        Random rand = new Random();
        ArrayList<Tecton> usedTectons = new ArrayList<>();
        for (Team team : teams) {
            Tecton rtecton1 = tectons.get(rand.nextInt(tectons.size()));
            while (usedTectons.contains(rtecton1)) {
                rtecton1 = tectons.get(rand.nextInt(tectons.size()));
            }
            Tecton rtecton2 = tectons.get(rand.nextInt(tectons.size()));
            while (usedTectons.contains(rtecton2)) {
                rtecton2 = tectons.get(rand.nextInt(tectons.size()));
            }
            team.setPositions(rtecton1, rtecton2);
            usedTectons.add(rtecton1);
            usedTectons.add(rtecton2);
        }
    }

    /**
     * A játék fő ciklusa. Végigiterál a csapatokon, és lehetővé teszi számukra a lépést.
     * A parancsokat az inputHandler-ből olvassa, és a commandParser dolgozza fel.
     */
    public void play() throws IOException {
        while (laps < 15) {
            for (int i = 0; i < teams.size(); i++) {
                currentTeam = teams.get(i);
                try {
                    String command = ioHandler.readLine();
                    commandParser.executeCommand(command);
                } catch (IOException e) {
                    ioHandler.writeLine("Hiba a parancs beolvasása közben: " + e.getMessage());
                }
            }
            incrementLap();
        }
        showResults();
    }

    /**
     * Kiírja az eredményeket, pontszámokat.
     */
    public void showResults() throws IOException {
        System.out.println("Játék vége! Eredmények:");
        for (Team team : teams) {
            ioHandler.writeLine(team.getName() + ": " + team.getScore() + " pont");
        }
    }

    /**
     * Tesztelési célra használt metódus, ami beolvassa a fájlt és soronként lefuttatja a parancsokat.
     */
    public void test() throws IOException {
        String line;
        try {
            while ((line = ioHandler.readLine()) != null) {
                commandParser.executeCommand(line);
            }
        } catch (IOException e) {
            ioHandler.writeLine("Hiba tesztelés közben: " + e.getMessage());
        }
    }

    /**
     * Hozzáad egy új csapatot a játékhoz.
     *
     * @param team A hozzáadni kívánt csapat.
     */
    public void addTeam(Team team) {
        teams.add(team);
    }
}

