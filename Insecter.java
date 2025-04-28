import java.util.ArrayList;
import java.util.List;

/**
 * Az Insecter osztály a rovarász rovarának kezelésére szolgál.
 */
public class Insecter extends Player{
    private List<Insect> insects;

    /**
     * Létrehoz egy új Insecter példányt.
     */
    public Insecter(String name) {
        super(name);
        insects = new ArrayList<Insect>();
    }

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

    /**
     * a paraméterül kapott rovart megette egy fonál, ezért a rovarász rovar tömbjéből kiveszi az adott rovart.
     *
     * @param insect A megevett Insect objektum.
     */
    public void insectEatenByString(Insect insect) {
        if(insect.getAbility().equals(Ability.PARALYZING)){
            if(insects.contains(insect)){
                insects.remove(insect);
            }
        }
    }
}
