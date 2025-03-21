import java.util.List;

public class MushroomBody {
    private List<MushroomString> strings;
    private List<Spore> spores;

    /**
     * A gombatestben található spórák számának csökkentése.
     */
    public void reduceSpore(Spore spore) {
        spores.remove(spore);
        System.out.println("MushroomBody reduceSpore");
    }

    /**
     * A gombatestben található spórák elszórása a környező tektonokra.
     */
    public void scatter(Spore spore) {
        //melyik tektonon van a gomba? honnan lehet tudni?
        //melyik tektonokra szórja el a gombát? honnan lehet tudni?
        System.out.println("MushroomBody scatter");
    }

    /**
     * Fonal növesztése a gombatestből.
     */
    public void createNewString() {
        MushroomString newString = new MushroomString();
        strings.add(newString);
        System.out.println("MushroomBody createNewString");
        //melyik teknon van a gombatest? kene tárolni ez nagyon elbaszott így

    }
}
