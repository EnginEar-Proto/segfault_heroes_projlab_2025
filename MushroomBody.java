import java.util.List;

public class MushroomBody {
    private List<MushroomString> strings;
    private List<Spore> spores;

    /**
     * A gombatestben található spórák számának csökkentése.
     * @param spore - A spóra, amelyet törölni kell.
     */
    public void reduceSpore(Spore spore) {
        spores.remove(spore);
        System.out.println("MushroomBody reduceSpore");
    }

    /**
     * A gombatestben található spórák elszórása a környező tektonokra.
     * A spórák számától függően szórja el a spórákat a környező tektonokra.
     */
    public void scatter() {
        //melyik tektonon van a gomba? honnan lehet tudni?
        //melyik tektonokra szórja el a gombát? honnan lehet tudni?
        //mennyi spórt szór szét?
        System.out.println("MushroomBody scatter");
        //getNeighbour distance??????????
        reduceSpore(spores.get(0));
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
