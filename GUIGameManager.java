import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A GameManager osztály felelős a játék teljes lefolyásáért.
 * Kezeli a körök számát, a játék végét, a csapatok lépéseit és a tektonok állapotát.
 */
public class GUIGameManager {
    private ArrayList<Tecton> tectons;
    private ArrayList<Team> teams;
    private int laps = 1;
    private Team currentTeam;
    private boolean gameStarted;


    public GUIGameManager() {
        this.tectons = new ArrayList<>();
        this.teams = new ArrayList<>();

        gameStarted = false;
    }

    private int currentTeamIndex = 0;
    private int currentTectonIndex = 0;
    private int currentInsectIndex = 0;
    private int currentMushroomBodyIndex = 0;
    private int currentMushroomStringIndex = 0;
    private int currentSporeIndex = 0;

    private GamePanel gamePanel;

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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

    private List<DrawableInterface> modelViewers = new ArrayList<>();
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
        Tecton tec5 = new Tecton("tek5", 2, true, 4, 5);
        tectons.add(tec5);
        Tecton tec6 = new Tecton("tek6", 3, false, 6, 5);
        tectons.add(tec6);
        Tecton tec7 = new Tecton("tek7", 3, false, 6, 7);
        tectons.add(tec7);
        Tecton tec8 = new Tecton("tek8", 2, true, 8, 8);
        tectons.add(tec8);
        Tecton tec9 = new Tecton("tek9", 2, true, 9, 8);
        tectons.add(tec9);
        Tecton tec10 = new Tecton("tek10", 1, true, 11, 7);
        tectons.add(tec10);
        Tecton tec11 = new Tecton("tek11", 1, false, 12, 7);
        tectons.add(tec11);
        Tecton tec12 = new Tecton("tek12", 3, false, 12, 5);
        tectons.add(tec12);
        Tecton tec13 = new Tecton("tek13", 3, false, 12, 3);
        tectons.add(tec13);
        Tecton tec14 = new Tecton("tek14", 3, false, 12, 1);
        tectons.add(tec14);
        Tecton tec15 = new Tecton("tek15", 2, true, 5, 9);
        tectons.add(tec15);
        Tecton tec16 = new Tecton("tek16", 3, true, 3, 9);
        tectons.add(tec16);
        Tecton tec17 = new Tecton("tek17", 1, false, 5, 11);
        tectons.add(tec17);
        Tecton tec18 = new Tecton("tek18", 2, false, 4, 12);
        tectons.add(tec18);
        Tecton tec19 = new Tecton("tek19", 2, false, 3, 13);
        tectons.add(tec19);
        Tecton tec20 = new Tecton("tek20", 2, true, 5, 13);
        tectons.add(tec20);
        Tecton tec21 = new Tecton("tek21", 3, false, 7, 13);
        tectons.add(tec21);
        Tecton tec22 = new Tecton("tek22", 2, false, 9, 12);
        tectons.add(tec22);
        Tecton tec23 = new Tecton("tek23", 3, true, 11, 11);
        tectons.add(tec23);
        Tecton tec24 = new Tecton("tek24", 1, false, 11, 10);
        tectons.add(tec24);
        Tecton tec25 = new Tecton("tek25", 1, false, 11, 9);
        tectons.add(tec25);

        tec1.initNeighbours(List.of(tec2, tec3));
        tec2.initNeighbours(List.of(tec3, tec1));
        tec3.initNeighbours(List.of(tec4, tec1, tec2));
        tec5.initNeighbours(List.of(tec6, tec4));
        tec6.initNeighbours(List.of(tec7, tec5));
        tec4.initNeighbours(List.of(tec5, tec3));
        tec7.initNeighbours(List.of(tec8, tec6, tec15));
        tec8.initNeighbours(List.of(tec7, tec9));
        tec9.initNeighbours(List.of(tec8, tec10, tec25));
        tec10.initNeighbours(List.of(tec9, tec11, tec12));
        tec11.initNeighbours(List.of(tec12, tec11));
        tec12.initNeighbours(List.of(tec11, tec13, tec10));
        tec13.initNeighbours(List.of(tec12, tec14));
        tec14.initNeighbours(List.of(tec13));
        tec15.initNeighbours(List.of(tec7, tec16));
        tec16.initNeighbours(List.of(tec15, tec17));
        tec17.initNeighbours(List.of(tec16, tec18));
        tec18.initNeighbours(List.of(tec17, tec19, tec20));
        tec19.initNeighbours(List.of(tec18, tec20));
        tec20.initNeighbours(List.of(tec18, tec19, tec21));
        tec21.initNeighbours(List.of(tec20, tec22));
        tec22.initNeighbours(List.of(tec21, tec23));
        tec23.initNeighbours(List.of(tec22, tec24));
        tec24.initNeighbours(List.of(tec23, tec25));
        tec25.initNeighbours(List.of(tec24, tec9));


    }

    /**
     * Beállítja a játékosok kezdőpozícióit.
     * Minden csapatnak kisorsol 2 tekton pozíciót.
     */
    public void setStartingPosition() throws IOException {
        if (tectons.size() * 2 < tectons.size()) {
            System.out.println("A kezőpozíciók nem oszthatóak ki, nem elegendő a tektonok száma");
        }
        Random rand = new Random();
        ArrayList<Tecton> usedTectons = new ArrayList<>();
        for (Team team : teams) {
            Tecton rtecton = tectons.get(rand.nextInt(tectons.size()));
            while (usedTectons.contains(rtecton)) {
                rtecton = tectons.get(rand.nextInt(tectons.size()));
            }
            team.setPositions(rtecton, rtecton);
            Insect ins = new Insect("ins" + currentInsectIndex++, 0, Ability.NORMAL, team.getInsecter(), rtecton);
            team.getInsecter().getInsects().add(ins);
            InsectView insView = new InsectView(ins);
            modelViewers.add(insView);
            MushroomBody mush = new MushroomBody("mbd" + currentMushroomBodyIndex++, rtecton);
            mush.loadBodyWithSpores(currentSporeIndex);
            team.getMushroomer().addMushroomBody(mush);
            usedTectons.add(rtecton);
        }
    }


    /**
     * Ellenőrzi, hogy a kiadot parancsot futtathatja-e gombász szerpű játékos.
     * @param action A parancs amely futtathatóságát ellenőrizzük
     * @return Futtatható gombász által.
     */
    private boolean checkMushroomerAction(String action){
        return List.of("growmushroombody", "growstring", "scatterspore", "eatinsect", "branch").contains(action);
    }

    /**
     * <p>
     *  Ellenőrzi, hogy a kiadott parancsot futtathatja-e a soron következő játékos.
     * </p>
     * <p>
     *  Az ellenőrzéshez felhasználja a játékos típusát, és a kiadott parancsot.
     *  Amennyiben az adott szerep futtathatja azt a parancsot a parancsértelemző is ellenőrzi helyességet.
     *  Ha helyes lefutásra kerül. Egyébként a szabványos kimeneten megjelenik a hibaüzenet.
     * </p>
     * @param isMushroomer Ez alapján ellenőrzi a szerepet
     * @param command A szabványos bemenetről beolvasott parancs
     * @return Logikai érték, amely igaz ha helyesen lefutott a parancs.
     */
    private boolean checkActionCorrectness(boolean isMushroomer, String command) throws IOException{
        return false;
    }

    private void PlayerStep(boolean isMushroomer) throws IOException{

    }

    /**
     * A játék fő ciklusa.
     * A parancsokat az inputHandler-ből olvassa, és a commandParser dolgozza fel.
     */
    public void play() throws IOException {
        /*
        while (laps < 15) {
            for (int i = 0; i < teams.size(); i++) {
                currentTeam = teams.get(i);
                ioHandler.writeLine("A " + currentTeam.getName() + " csapat következik.");
                ioHandler.writeLine("A " + currentTeam.getName() + " csapat gombásza következik.");
                try {
                    PlayerStep(true);
                } catch (IOException e) {
                    ioHandler.writeLine("Hiba a parancs beolvasása közben: " + e.getMessage());
                }

                ioHandler.writeLine("A " + currentTeam.getName() + " csapat rovarásza következik:");
                try {
                    PlayerStep(false);
                } catch (IOException e) {
                    ioHandler.writeLine("Hiba a parancs beolvasása közben: " + e.getMessage());
                }
            }
            incrementLap();
        }
        gameStarted = false;
        showResults();*/
    }

    /**
     * Kiírja az eredményeket, pontszámokat.
     */
    public void showResults() throws IOException {

    }

    /**
     * Hozzáad egy új csapatot a játékhoz.
     *
     * @param team A hozzáadni kívánt csapat.
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * Hozzáad egy új tektont a játékhoz.
     *
     * @param t A hozzáadni kívánt tekton.
     */
    public void addTecton(Tecton t) {
        tectons.add(t);
    }

    public void render(Graphics g) {
        for (DrawableInterface d : modelViewers) {
            d.draw(g);
        }
    }
}


