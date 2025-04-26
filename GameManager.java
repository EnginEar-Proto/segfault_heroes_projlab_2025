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
    private boolean gameStarted;

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

        gameStarted = false;
    }

    public boolean getGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public Team[] getTeams() {
        return teams.toArray(new Team[teams.size()]);
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

    public int getLaps() {
        return laps;
    }

    /**
     * Létrehozza a játék kezdőpályáját.
     * Inicializálja a tektonokat előre definiált módon.
     */
    public void initializeMap() {
        tectons.add(new Tecton("tekton1", 3, true, 1, 1));
        tectons.add(new Tecton("tekton2", 2, true, 3, 2));
        tectons.add(new Tecton("tekton3", 1, true, 2, 3));
        tectons.add(new Tecton("tekton4", 3, true, 2, 4));
        tectons.add(new Tecton("tekton5", 2, true, 3, 5));
        tectons.add(new Tecton("tekton6", 3, true, 6, 5));
        tectons.add(new Tecton("tekton7", 1, true, 8, 8));
        tectons.add(new Tecton("tekton8", 2, true, 9, 8));
        tectons.add(new Tecton("tekton9", 1, true, 11, 7));
        tectons.add(new Tecton("tekton10", 1, true, 12, 7));
        tectons.add(new Tecton("tekton11", 3, true, 12, 5));
        tectons.add(new Tecton("tekton12", 3, true, 12, 3));
        tectons.add(new Tecton("tekton13", 3, true, 12, 1));
        tectons.add(new Tecton("tekton14", 2, true, 5, 9));
        tectons.add(new Tecton("tekton15", 3, true, 3, 9));
        tectons.add(new Tecton("tekton16", 1, true, 5, 11));
        tectons.add(new Tecton("tekton17", 2, true, 4, 12));
        tectons.add(new Tecton("tekton18", 2, true, 3, 13));
        tectons.add(new Tecton("tekton19", 2, true, 5, 13));
        tectons.add(new Tecton("tekton20", 3, true, 7, 13));
        tectons.add(new Tecton("tekton21", 2, true, 9, 12));
        tectons.add(new Tecton("tekton22", 3, true, 11, 11));
        tectons.add(new Tecton("tekton23", 1, true, 11, 10));
        tectons.add(new Tecton("tekton24", 1, true, 11, 9));
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
            Tecton rtecton = tectons.get(rand.nextInt(tectons.size()));
            while (usedTectons.contains(rtecton)) {
                rtecton = tectons.get(rand.nextInt(tectons.size()));
            }
            team.setPositions(rtecton, rtecton);
            usedTectons.add(rtecton);
        }
    }

    /**
     * Kilistázza a jelenlegi csapatokat és a hozzájuk tartozó tektont.
     */
    public void listTeams() throws IOException {
        ioHandler.writeLine("Teams:");
        for (Team team : teams) {
            ioHandler.writeLine(team.getName() + " (" + team.getMushroomer().getName() + " + " + team.getInsecter().getName() + ") tecton: " + team.getStartTecton().getId());
        }
    }

    /**
     * A játék fő ciklusa.
     * A parancsokat az inputHandler-ből olvassa, és a commandParser dolgozza fel.
     */
    public void play() throws IOException {
        while (laps < 15) {
            for (int i = 0; i < teams.size(); i++) {
                currentTeam = teams.get(i);
                try {
                    String command = ioHandler.readLine();
                    boolean exit = commandParser.executeCommand(command);
                    if (!exit) {
                        gameStarted = false;
                        return;
                    }
                } catch (IOException e) {
                    ioHandler.writeLine("Hiba a parancs beolvasása közben: " + e.getMessage());
                }
            }
            incrementLap();
        }
        gameStarted = false;
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

