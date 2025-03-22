import java.util.List;
import java.util.Map;

public class MushroomBody {
    private List<MushroomString> strings;
    private List<Spore> spores;
    private Tecton tecton;

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
     * @param toScatter - A tekton, amelyre a spórákat el kell szórni.
     *
     */
    public void scatter(Tecton toScatter) {
        int distance = tecton.neighbourDistance(toScatter);
        if(distance > 3) {
            System.out.println("Can't scatter spores, tectons are too far");
            return;
        }
        System.out.println("MushroomBody scatter");
        for(int i = 0; i < distance; i++) {
            Spore sp = spores.getFirst();
            toScatter.scatterSpore(sp);
            reduceSpore(sp);
        }
    }

    /**
     * Fonal növesztése a gombatestből.
     */
    public void createNewString() {
        MushroomString newString = new MushroomString(1, List.of(this), List.of(tecton), List.of(Map.of()));
        strings.add(newString);
        tecton.addNewString(newString);
        System.out.println("MushroomBody createNewString");
        //melyik teknon van a gombatest? kene tárolni ez nagyon elbaszott így

    }
}
