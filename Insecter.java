/**
 * Az Insecter osztály a rovarász rovarának kezelésére szolgál.
 */
public class Insecter {
    private Insect insect;

    /**
     * Létrehoz egy új Insecter példányt.
     */
    public Insecter() {}

    /**
     * Visszaadja a tárolt rovar objektumot.
     *
     * @return Az aktuálisan tárolt Insect objektum.
     */
    public Insect getInsect() {
        return insect;
    }

    /**
     * Beállítja a rovar objektumot.
     *
     * @param i Az új Insect objektum.
     */
    public void setInsect(Insect i) {
        insect = i;
    }
}
