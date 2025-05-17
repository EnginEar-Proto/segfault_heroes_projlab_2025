import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MushroomBody {
    private String id;
    private List<MushroomString> strings;
    private List<Spore> spores;
    private Tecton tecton;

    public MushroomBody(String id, Tecton t){
        this.id = id;
        strings = new ArrayList<>();
        spores = new ArrayList<>();
        tecton = t;
        t.setMushroomBody(this);



    }

    public int loadBodyWithSpores(int startIndex) {
        Spore s1 = new Spore("spr" + startIndex++,1, Ability.DIVIDER);
        Spore s2 = new Spore("spr" + startIndex++,2, Ability.NORMAL);
        Spore s3 = new Spore("spr" + startIndex++,3, Ability.NORMAL);
        Spore s4 = new Spore("spr" + startIndex++,1, Ability.DIVIDER);
        Spore s5 = new Spore("spr" + startIndex++,2, Ability.PARALYZING);
        Spore s6 = new Spore("spr" + startIndex++,2, Ability.PARALYZING);
        Spore s7 = new Spore("spr" + startIndex++,2, Ability.SWIFT);
        Spore s8 = new Spore("spr" + startIndex++,2, Ability.SWIFT);
        spores.add(s1);
        spores.add(s2);
        spores.add(s3);
        spores.add(s4);
        spores.add(s5);
        spores.add(s6);
        spores.add(s7);
        spores.add(s8);
        return startIndex;
    }

    public void initializeSporeViews(TeamColor color) {
        for (Spore s: spores) {
            try {
                SporeView sv = new SporeView(s, color);
                GUIGameManager.modelViewers.add(sv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getID() {
        return id;
    }

    public List<MushroomString> getStrings() {
        return strings;
    }

    public List<Spore> getSpores() {
        return spores;
    }

    public Tecton getTecton() {
        return tecton;
    }

    /**
     * A gombatestben található spórák számának csökkentése.
     * @param spore - A spóra, amelyet törölni kell.
     */
    public void reduceSpore(Spore spore) {
        spores.remove(spore);
    }

    /**
     * A gombatestben található spórák elszórása a környező tektonokra.
     * A spórák számától függően szórja el a spórákat a környező tektonokra.
     * @param toScatter - A tekton, amelyre a spórákat el kell szórni.
     * @return -1, ha a spórák száma nem elegendő az elszóráshoz, vagy ha a tekton túl messze van.
     * @return a spórák száma, ha a szórás sikeres.
     */
    public int scatter(Tecton toScatter, Ability ab) {
        int distance = tecton.neighbourDistance(toScatter);
        List<Spore> givenSpores = new ArrayList<>();
        for(Spore spore : spores) {
            if(spore.getAbility().equals(ab)) {
                givenSpores.add(spore);
            }
        }
        if(distance >= 4 || distance == -1) {
            return -1;
        }
        if(distance > givenSpores.size()) {
            return -1;
        }
        Spore sp = null;
        for(int i = 0; i < distance-1; i++) {
            sp = givenSpores.get(0);
            reduceSpore(sp);
        }
        sp = givenSpores.get(0);
        toScatter.scatterSpore(sp);
        sp.setTecton(toScatter);
        return spores.size();
    }

    /**
     * Fonal növesztése a gombatestből.
     */
    public void createNewString() {
        MushroomString newString = new MushroomString("str_" + id, this, tecton);
        strings.add(newString);
        tecton.addNewString(newString);
    }

    /**
     * Fonal hozzáadása a fonal listához.
     */
    public void addString(MushroomString string) {
        strings.add(string);
    }

    /**
     * Fonal eltávolítása a fonal listából.
     */
    public void removeString(MushroomString string) {
        strings.remove(string);
    }
}
