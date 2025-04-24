import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MushroomBody {
    private List<MushroomString> strings;
    private List<Spore> spores;
    private Tecton tecton;

    public MushroomBody(Tecton t){
        strings = new ArrayList<>();
        spores = new ArrayList<>();
        tecton = t;
    }

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
     * @return -1, ha a spórák száma nem elegendő az elszóráshoz, vagy ha a tekton túl messze van.
     * @return a spórák száma, ha a szórás sikeres.
     */
    public int scatter(Tecton toScatter) {
        int distance = tecton.neighbourDistance(toScatter);
        if(distance >= 4 || distance == -1) {
            System.out.println("Can't scatter spores, tectons are too far");
            return -1;
        }
        if(distance > spores.size()) {
            System.out.println("Not enough spores to scatter");
            return -1;
        }
        System.out.println("MushroomBody scatter");
        Spore sp;
        for(int i = 0; i < distance-1; i++) {
            sp = spores.getFirst();
            reduceSpore(sp);
        }
        sp = spores.getFirst();
        toScatter.scatterSpore(sp);
        return spores.size();
    }

    /**
     * Fonal növesztése a gombatestből.
     */
    public void createNewString() {
        MushroomString newString = new MushroomString(this, tecton);
        strings.add(newString);
        tecton.addNewString(newString);
        System.out.println("MushroomBody createNewString");
    }

    /**
     * Fonal hozzáadása a fonal listához.
     */
    public void addString(MushroomString string) {
        strings.add(string);
        System.out.println("MushroomBody addString");
    }

    /**
     * Fonal eltávolítása a fonal listából.
     */
    public void removeString(MushroomString string) {
        strings.remove(string);
        System.out.println("MushroomBody removeString");
    }
}
