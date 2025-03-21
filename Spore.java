public class Spore {
    private int substance;
    private Ability ability;
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

    /**
     * A spóra elfogyasztásra kerül.
     * A spórát elfogyasztó rovaron a spóra hatása érvényesül.
     */
    public void getEaten(Insect insect) {
        insect.applyEffect(this);
    }
}
