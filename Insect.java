import java.util.Scanner;

public class Insect {
    private int substance;
    private Ability ability;
    private Tecton tecton;
    public Insect(int substance, Ability ability) {
        this.substance = substance;
        this.ability = ability;
    }
    public int getSubstance() {
        return substance;
    }
    public Ability getAbility() {
        return ability;
    }
    public void setAbility(Ability ability) {
        this.ability = ability;
    }
    public Tecton getTecton() {
        return tecton;
    }
    public void setTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * A rovar elfogyasztja a spórát, amelynek hatása érvénybe lép.
     * Tovább hívja a spore getEaten függvényét.
     */
    public void eat(Spore spore){
        System.out.println("Insect eats spore");
        spore.getEaten(this);

    }

    /**
     * A rovar a paraméterben kapott tektonra lép, amennyiben erre lehetősége van.
     */
    public void moveTo(Tecton tecton){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Can insect move to given tecton? [y/n]");
        String answer=scanner.nextLine();
        if(answer.equals("y")) {
            System.out.println("Insect moves to given tecton");
            this.tecton = tecton;
        }else if(answer.equals("n")) {
            System.out.println("Insect cannot move to given tecton");
        }else{
            System.out.println("You gave incorrect character!");
        }
    }

    /**
     * A rovar elvágja a paraméterül kapott fonalat.
     */
    public void sabotageString(MushroomString string){
        System.out.println("Insect cuts string");
    }

    /**
     * A rovar pontjainak növelése
     */
    public void addSubstance(int substance){
        System.out.println("Substance level added to insect");
        this.substance += substance;
    }
}
