import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        Tecton tec1 = new Tecton("tek1", 3, false, 1, 1);
        tectons.add(tec1);
        Tecton tec2 = new Tecton("tek2", 2, true, 3, 2);
        tectons.add(tec2);
        Tecton tec3 = new Tecton("tek3", 1, false, 2, 3);
        tectons.add(tec3);
        Tecton tec4 = new Tecton("tek4", 3, true, 2, 4);
        tectons.add(tec4);
        Tecton tec5 = new Tecton("tek5", 2, true, 3, 5);
        tectons.add(tec5);
        Tecton tec6 = new Tecton("tek6", 3, false, 6, 5);
        tectons.add(tec6);
        Tecton tec7 = new Tecton("tek7", 1, false, 8, 8);
        tectons.add(tec7);
        Tecton tec8 = new Tecton("tek8", 2, false, 9, 8);
        tectons.add(tec8);
        Tecton tec9 = new Tecton("tek9", 1, true, 11, 7);
        tectons.add(tec9);
        Tecton tec10 = new Tecton("tek10", 1, true, 12, 7);
        tectons.add(tec10);
        Tecton tec11 = new Tecton("tek11", 3, false, 12, 5);
        tectons.add(tec11);
        Tecton tec12 = new Tecton("tek12", 3, false, 12, 3);
        tectons.add(tec12);
        Tecton tec13 = new Tecton("tek13", 3, false, 12, 1);
        tectons.add(tec13);
        Tecton tec14 = new Tecton("tek14", 2, false, 5, 9);
        tectons.add(tec14);
        Tecton tec15 = new Tecton("tek15", 3, true, 3, 9);
        tectons.add(tec15);
        Tecton tec16 = new Tecton("tek16", 1, true, 5, 11);
        tectons.add(tec16);
        Tecton tec17 = new Tecton("tek17", 2, false, 4, 12);
        tectons.add(tec17);
        Tecton tec18 = new Tecton("tek18", 2, false, 3, 13);
        tectons.add(tec18);
        Tecton tec19 = new Tecton("tek19", 2, false, 5, 13);
        tectons.add(tec19);
        Tecton tec20 = new Tecton("tek20", 3, true, 7, 13);
        tectons.add(tec20);
        Tecton tec21 = new Tecton("tek21", 2, false, 9, 12);
        tectons.add(tec21);
        Tecton tec22 = new Tecton("tek22", 3, false, 11, 11);
        tectons.add(tec22);
        Tecton tec23 = new Tecton("tek23", 1, true, 11, 10);
        tectons.add(tec23);
        Tecton tec24 = new Tecton("tek24", 1, false, 11, 9);
        tectons.add(tec24);

        tec1.setNeighbours(List.of(tec2, tec3));
        tec2.setNeighbours(List.of(tec3, tec1));
        tec3.setNeighbours(List.of(tec4, tec1, tec2));
        tec4.setNeighbours(List.of(tec5, tec3));
        tec5.setNeighbours(List.of(tec6, tec4));
        tec6.setNeighbours(List.of(tec7, tec5));
        tec7.setNeighbours(List.of(tec8, tec6, tec14));
        tec8.setNeighbours(List.of(tec7, tec9));
        tec9.setNeighbours(List.of(tec8, tec10, tec11, tec21));
        tec10.setNeighbours(List.of(tec9, tec12));
        tec11.setNeighbours(List.of(tec9, tec12, tec22));
        tec12.setNeighbours(List.of(tec11, tec13, tec10));
        tec13.setNeighbours(List.of(tec12));
        tec14.setNeighbours(List.of(tec7, tec15));
        tec15.setNeighbours(List.of(tec14, tec16));
        tec16.setNeighbours(List.of(tec15, tec17));
        tec17.setNeighbours(List.of(tec16, tec18));
        tec18.setNeighbours(List.of(tec17, tec19));
        tec19.setNeighbours(List.of(tec18, tec20));
        tec20.setNeighbours(List.of(tec19, tec21));
        tec21.setNeighbours(List.of(tec20, tec22));
        tec22.setNeighbours(List.of(tec21, tec23, tec24));
        tec23.setNeighbours(List.of(tec22));
        tec24.setNeighbours(List.of(tec22));


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
            if (team.getStartTecton() != null) ioHandler.writeLine(team.getName() + " (" + team.getMushroomer().getName() + " + " + team.getInsecter().getName() + ") tecton: " + team.getStartTecton().getId());
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
                ioHandler.writeLine("A " + currentTeam.getName() + " csapat következik.");
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

