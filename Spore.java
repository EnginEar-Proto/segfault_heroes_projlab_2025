public class Spore {
    private int substance;
    private Ability ability;
    private Tecton tecton;
    public Spore(int substance, Ability ability) {
        this.substance = substance;
        this.ability = ability;
    }
    public int getSubstance() {
        return substance;
    }
    public Ability getAbility() {
        return ability;
    }
    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * A spóra elfogyasztásra kerül.
     * A spórát elfogyasztó rovaron a spóra hatása érvényesül.
     * A spóra tápanyag tartalmával nő a rovar tápanyag tartalma.
     */
    public void getEaten(Insect insect) {
        System.out.println("Spore gets eaten");
        insect.addSubstance(substance);
        insect.setAbility(ability);
    }
}
