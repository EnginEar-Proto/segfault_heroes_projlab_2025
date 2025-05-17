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
    private boolean isCurrentPlayerMushroomer = true;
    private boolean gameStarted;
    private Board board;


    public GUIGameManager() {
        this.tectons = new ArrayList<>();
        this.teams = new ArrayList<>();
        board = new Board();
        modelViewers.add(board);
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

    public static List<DrawableInterface> modelViewers = new ArrayList<>();

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
        Tecton tec8 = new Tecton("tek8", 1, true, 8, 8);
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

        try{
            TectonView tv1 = new TectonView(tec1);
            modelViewers.add(tv1);
            TectonView tv2 = new TectonView(tec2);
            modelViewers.add(tv2);
            TectonView tv3 = new TectonView(tec3);
            modelViewers.add(tv3);
            TectonView tv4 = new TectonView(tec4);
            modelViewers.add(tv4);
            TectonView tv5 = new TectonView(tec5);
            modelViewers.add(tv5);
            TectonView tv6 = new TectonView(tec6);
            modelViewers.add(tv6);
            TectonView tv7 = new TectonView(tec7);
            modelViewers.add(tv7);
            TectonView tv8 = new TectonView(tec8);
            modelViewers.add(tv8);

            TectonView tv9 = new TectonView(tec9);
            modelViewers.add(tv9);
            TectonView tv10 = new TectonView(tec10);
            modelViewers.add(tv10);
            TectonView tv11 = new TectonView(tec11);
            modelViewers.add(tv11);
            TectonView tv12 = new TectonView(tec12);
            modelViewers.add(tv12);
            TectonView tv13 = new TectonView(tec13);
            modelViewers.add(tv13);
            TectonView tv14 = new TectonView(tec14);
            modelViewers.add(tv14);
            TectonView tv15 = new TectonView(tec15);
            modelViewers.add(tv15);
            TectonView tv16 = new TectonView(tec16);
            modelViewers.add(tv16);

            TectonView tv17 = new TectonView(tec17);
            modelViewers.add(tv17);
            TectonView tv18 = new TectonView(tec18);
            modelViewers.add(tv18);
            TectonView tv19 = new TectonView(tec19);
            modelViewers.add(tv19);
            TectonView tv20 = new TectonView(tec20);
            modelViewers.add(tv20);
            TectonView tv21 = new TectonView(tec21);
            modelViewers.add(tv21);
            TectonView tv22 = new TectonView(tec22);
            modelViewers.add(tv22);
            TectonView tv23 = new TectonView(tec23);
            modelViewers.add(tv23);
            TectonView tv24 = new TectonView(tec24);
            modelViewers.add(tv24);
            TectonView tv25 = new TectonView(tec25);
            modelViewers.add(tv25);
        }catch (Exception e){
            e.printStackTrace();
        }

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
            System.out.println("A kezdőpozíciók nem oszthatóak ki, nem elegendő a tektonok száma");
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
            MushroomBody mush = new MushroomBody("mbd" + currentMushroomBodyIndex++, rtecton);
            mush.loadBodyWithSpores(currentSporeIndex);
            team.getMushroomer().addMushroomBody(mush);
            mush.initializeSporeViews(team.getColor());
            MushroomBodyView msbView = new MushroomBodyView(mush, team.getColor());
            modelViewers.add(msbView);
            InsectView insView = new InsectView(ins, team.getColor());
            modelViewers.add(insView);
            usedTectons.add(rtecton);

            Tecton startTecton = rtecton;
            Tecton destTecton = rtecton.getNeighbours().getFirst();

            MushroomString s = new MushroomString("str1", startTecton.getMushroomBody(), List.of(startTecton), null);
            s.growTo(startTecton, startTecton);
            s.growTo(startTecton, destTecton);

            mush.scatter(destTecton, Ability.NORMAL);
            mush.scatter(destTecton, Ability.NORMAL);
            mush.scatter(destTecton, Ability.NORMAL);

        }
        currentTeam = teams.get(0);
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

    private void PlayerStep(IOPanel ioPanel) throws IOException {
        if (isCurrentPlayerMushroomer) {
            ioPanel.updateState(laps, currentTeam, "Insecter", currentTeam.getScore(), currentTeam.getMushroomer().getMushroomBodies().size());
            if (++laps > 15)
                ioPanel.showResults(); //Geri írd meg pls
        }
        else {
            int nextIndex = (teams.indexOf(currentTeam) + 1) % teams.size();
            currentTeam = teams.get(nextIndex);
            ioPanel.updateState(laps, currentTeam, "Mushroomer", currentTeam.getScore(), currentTeam.getMushroomer().getMushroomBodies().size());
        }
    } //Kopi: készen vagyunk, pusholhatod

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

    /**
     * A megadott képernyő koordináták alapján visszaadja azt a tektont, amelyik azon a koordinátán van.
     * @param x
     * @param y
     */
    public Tecton getTectonByCoords(int x, int y) {
        Tecton res = null;
        for (int i = 0; i < modelViewers.size(); i++){
            TectonView model;
            try{
                model = (TectonView) modelViewers.get(i);
                res = model.getModel();
            } catch (Exception e){
                continue;
            }

            int[] pos = res.getPosition();
            int[] pixelPos = {pos[0] * Board.SQUARE_SIZE, pos[1] * Board.SQUARE_SIZE};
            int[] posEnd = {pixelPos[0] + Board.SQUARE_SIZE, pixelPos[1] + Board.SQUARE_SIZE};
            switch (res.getSize()) {
                case 3:
                    posEnd[0] = pixelPos[0] + Board.SQUARE_SIZE * 2;
                    posEnd[1] = pixelPos[1] + Board.SQUARE_SIZE * 2;
                    break;
                case 2:
                    posEnd[0] = pixelPos[0] + Board.SQUARE_SIZE * 2;
                    break;
            }

            if(pixelPos[0] > x || posEnd[0] < x) {res = null; continue;}
            if(pixelPos[1] > y || posEnd[1] < y) {res = null; continue;}

            break;
        }

        return res;
    }
}


