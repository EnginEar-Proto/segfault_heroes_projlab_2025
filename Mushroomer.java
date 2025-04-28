import java.util.ArrayList;
import java.util.List;

/**
 * A Mushroomer osztály a gombász gombatestjeinek kezelésére szolgál.
 */
public class Mushroomer extends Player{
    private ArrayList<MushroomBody> mushroomBodies;

    /**
     * Létrehoz egy új Mushroomer példányt, amely üres gombatestek listájával rendelkezik.
     */
    public Mushroomer(String name) {
        super(name);
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
        try {
            return mushroomBodies.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public List<MushroomBody> getMushroomBodies() {
        return mushroomBodies;
    }
}
