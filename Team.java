/**
 * A Team osztály egy csapatot reprezentál, amely egy gombászból és egy rovarászból áll.
 */
public class Team {
    private Insecter insecter;
    private Mushroomer mushroomer;
    private int score;
    private String name;
    private Tecton startTecton;
    private TeamColor color;

    public Team() {}

    public Team(String name, Insecter insecter, Mushroomer mushroomer, TeamColor color) {
        this.name = name;
        this.insecter = insecter;
        this.mushroomer = mushroomer;
        this.color = color;
    }

    public TeamColor getColor() { return color; }
    /**
     * Visszaadja a csapat kezdő tektonját.
     * @return a csapat kezdő tektonja.
     */
    public Tecton getStartTecton() {
        return startTecton;
    }

    /**
     * Beállítja a csapat játékosait.
     *
     * @param mushroomer A csapathoz tartozó Mushroomer objektum.
     * @param insecter A csapathoz tartozó Insecter objektum.
     */
    public void setPlayers(Mushroomer mushroomer, Insecter insecter) {
        this.mushroomer = mushroomer;
        this.insecter = insecter;
    }

    /**
     * Visszaadja a csapat gombászát.
     *
     * @return A csapathoz tartozó Mushroomer objektum.
     */
    public Mushroomer getMushroomer() {
        return mushroomer;
    }

    /**
     * Visszaadja a csapat rovarászát.
     *
     * @return A csapathoz tartozó Insecter objektum.
     */
    public Insecter getInsecter() {
        return insecter;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        int res = 0;
        for(int i=0; i<insecter.getInsects().size(); i++) {
            res += insecter.getInsects().get(i).getSubstance();
        }
        score = res;
        return score;
    }

    /**
     * Beállítja a csapat játékosainak pozícióit.
     *
     * @param t1 Az első Tecton objektum, amelyre a rovar kerül elhelyezésre.
     * @param t2 A második Tecton objektum, amelyre a gombatest kerül elhelyezésre.
     */
    public void setPositions(Tecton t1, Tecton t2) {
        if(insecter.getInsects().size()>0) {
            t1.setBugPosition(insecter.getInsects().get(0));
        }
        t2.setMushroomBody(mushroomer.getMushroomBody(0));
        startTecton = t1;
    }
}
