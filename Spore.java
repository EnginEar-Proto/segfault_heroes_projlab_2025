public class Spore {
    private String id;
    private int substance;
    private Ability ability;
    private Tecton tecton;
    public Spore(String id, int substance, Ability ability) {
        this.substance = substance;
        this.ability = ability;
        this.id = id;
    }
    public int getSubstance() {
        return substance;
    }
    public Ability getAbility() {
        return ability;
    }

    /**
     * Visszaadja a spóra azonosítóját.
     * @return A spóra azonosítója.
    */
    public String getId(){
        return this.id;
    }

    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }
    public Tecton getTecton() {
        return tecton;
    }
    /**
     * A spóra elfogyasztásra kerül.
     * A spórát elfogyasztó rovaron a spóra hatása érvényesül.
     * A spóra tápanyag tartalmával nő a rovar tápanyag tartalma.
     */
    public void getEaten(Insect insect) {
        insect.addSubstance(substance);
        insect.setAbility(ability);
        if(insect.getAbility().equals(Ability.DIVIDER)){
            insect.getInsecter().addInsect(new Insect(insect.getId() + "_div", 0, Ability.NORMAL, insect.getInsecter(), insect.getTecton()));
        }
        insect.getTecton().removeSpore(this);
    }
}
