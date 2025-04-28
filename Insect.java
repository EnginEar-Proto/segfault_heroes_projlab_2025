import java.util.Scanner;

/**
 * Az Insect osztály egy rovart reprezentál, amely különböző műveleteket hajthat végre,
 * például mozgást, spórák elfogyasztását és fonalak elvágását.
 */
public class Insect {
    private String id;
    private int substance; // A rovar által birtokolt anyagok mennyisége
    private Ability ability; // A rovar képessége
    private Tecton tecton; // A rovar aktuális helyzete
    private Insecter insecter; //A rovart irányító rovarász

    /**
     * Konstruktor, amely beállítja a rovar anyagmennyiségét és képességét.
     * @param substance A rovar kezdeti anyagmennyisége.
     * @param ability A rovar kezdeti képessége.
     */
    public Insect(String id, int substance, Ability ability, Insecter insecter, Tecton tecton) {
        this.id = id;
        this.substance = substance;
        this.ability = ability;
        this.insecter = insecter;
        this.tecton = tecton;
        tecton.addInsect(this);
    }

    public String getId() {
        return id;
    }

    /**
     * Visszaadja a rovar anyagmennyiségét.
     * @return A rovar aktuális anyagmennyisége.
     */
    public int getSubstance() {
        return substance;
    }

    /**
     * Visszaadja a rovar azonosítóját.
     * @return A rovar azonosítója.
    */
    public String getid(){
        return this.id;
    }

    /**
     * Visszaadja a rovar képességét.
     * @return A rovar aktuális képessége.
     */
    public Ability getAbility() {
        return ability;
    }

    /**
     * Visszaadja a rovart irányító rovarászt
     * @return rovarász
     */
    public Insecter getInsecter() {
        return insecter;
    }

    /**
     * Beállítja a rovar új képességét.
     * @param ability Az új képesség.
     */
    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    /**
     * Visszaadja a rovar aktuális helyzetét.
     * @return A rovar által elfoglalt Tecton objektum.
     */
    public Tecton getTecton() {
        return tecton;
    }

    /**
     * Beállítja a rovar új helyzetét.
     * @param tecton Az új Tecton objektum, amelyre a rovar kerül.
     */
    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * A rovar elfogyasztja a spórát, amelynek hatása érvénybe lép.
     * Továbbhívja a spore getEaten függvényét.
     * @param spore A spóra, amelyet a rovar elfogyaszt.
     */
    public void eat(Spore spore){
        spore.getEaten(this);
    }

    /**
     * A rovar megpróbál a paraméterként kapott tektonra lépni.
     * A mozgás csak akkor történik meg, ha a felhasználó engedélyezi azt.
     * @param tecton A cél Tecton objektum, ahova a rovar lépni szeretne.
     */
    public void moveTo(Tecton tecton){
        for(int i = 0; i < this.tecton.getStrings().size(); i++) {
            MushroomString str = this.tecton.getStrings().get(i);
            if (tecton.getStrings().contains(str)) {
                this.tecton.removeInsect(this);
                this.tecton = tecton;
                this.tecton.addInsect(this);
                break;
            }
        }
    }

    /**
     * A rovar elvágja a paraméterként kapott fonalat.
     * @param string A MushroomString objektum, amelyet el kell vágni.
     */
    public int sabotageString(MushroomString string, Tecton t1, Tecton t2){
        return string.cut(t1, t2);
    }

    /**
     * Növeli a rovar anyagmennyiségét a paraméterként megadott értékkel.
     * @param substance A hozzáadandó anyagmennyiség.
     */
    public void addSubstance(int substance){
        this.substance += substance;
    }
}