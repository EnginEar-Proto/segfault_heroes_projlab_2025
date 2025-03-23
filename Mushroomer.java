import java.util.ArrayList;

/**
 * A Mushroomer osztály a gombász gombatestjeinek kezelésére szolgál.
 */
public class Mushroomer {
    private ArrayList<MushroomBody> mushroomBodies;

    /**
     * Létrehoz egy új Mushroomer példányt, amely üres gombatestek listájával rendelkezik.
     */
    public Mushroomer() {
        mushroomBodies = new ArrayList<MushroomBody>();
    }

    /**
     * Hozzáad egy új gombatestet a listához.
     *
     * @param m A hozzáadni kívánt MushroomBody objektum.
     */
    public void addMushroomBody(MushroomBody m) {
        mushroomBodies.add(m);
    }

    /**
     * Visszaadja a megadott indexű gombatestet.
     *
     * @param index Az elérni kívánt MushroomBody indexe.
     * @return A megfelelő MushroomBody objektum, vagy null, ha nincs ilyen index.
     */
    public MushroomBody getMushroomBody(int index) {
        return mushroomBodies.get(index) == null ? null : mushroomBodies.get(index);
    }
}
