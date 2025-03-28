import java.util.ArrayList;
import java.util.List;

/**
 * Az Insecter osztály a rovarász rovarának kezelésére szolgál.
 */
public class Insecter {
    private List<Insect> insects = new ArrayList<Insect>();

    /**
     * Létrehoz egy új Insecter példányt.
     */
    public Insecter() {}

    /**
     * Visszaadja a tárolt rovar objektumot.
     *
     * @return Az aktuálisan tárolt Insect objektum.
     */
    public List<Insect> getInsects() {
        return insects;
    }

    /**
     * Hozzáad egy rovar objektumot, a rovarok listához.
     *
     * @param i Az új Insect objektum.
     */
    public void addInsect(Insect i) {
        insects.add(i);
    }


    public void insectEatenByString(Insect insect) {
        if(insect.getAbility().equals(Ability.PARALYZING)){
            System.out.println("Insect was eaten by a string!");
            if(insects.contains(insect)){
                insects.remove(insect);
            }
        }else{
            System.out.println("Insect cannot be eaten!");
        }
    }
}
