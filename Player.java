/**
 * A Player osztály egy játékost reprezentál névvel.
 */
public class Player {
    private String name;

    /**
     * Létrehoz egy új Player példányt a megadott névvel.
     *
     * @param name A játékos neve.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return A játékos neve.
     */
    public String getName() {
        return name;
    }
}
