public class Team {
    private Insecter insecter;
    private Mushroomer mushroomer;
    private int score;
    private String name;

    public void setPlayers(Mushroomer mushroomer, Insecter insecter) {
        this.mushroomer = mushroomer;
        this.insecter = insecter;
    }

    public Mushroomer getMushroomer() {
        return mushroomer;
    }

    public Insecter getInsecter() {
        return insecter;
    }

    public void setPositions(Tecton t1, Tecton t2) {
        t1.setBugPosition(insecter.getInsect());
        t2.setMushroomBody(mushroomer.getMushroomBody(0));
    }
}
