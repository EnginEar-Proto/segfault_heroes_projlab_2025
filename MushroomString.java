import java.util.List;
import java.util.Scanner;


public class MushroomString {
    private int length;
    private List<MushroomBody> mushroomBodies;
    private List<Tecton> tectons;

    MushroomString(int length, List<MushroomBody> mushroomBodies, List<Tecton> tectons) {
        this.length = length;
        this.mushroomBodies = mushroomBodies;
        this.tectons = tectons;
    }

    /**
     * Gombatest hozzáadása a gombaszálhoz.
     * @param mushroomBody
     */
    public void addMushroomBody(MushroomBody mushroomBody) {
        mushroomBodies.add(mushroomBody);
    }

    /**
     * Elvágja a gombaszálat.
     * A gombaszálból 2 rész marad, ha mindkét oldalt van gombatest ami táblálja, egyébként a gombaszál egyik fele törlődik.
     */
    public void cut(Tecton t1, Tecton t2) {
        if(t1.neighbourDistance(t2) != 1) {
            System.out.println("Can't cut MushroomString, tectons are not neighbours");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is there mushroomBody on both end?[y/n]: ");
        String input = scanner.nextLine();
        int leftIndex = tectons.indexOf(t1);
        int rightIndex = tectons.indexOf(t2);
        if(input.equals("y") && mushroomBodies.size() >= 2) {
            if(leftIndex > rightIndex) {
                int temp = leftIndex;
                leftIndex = rightIndex;
                rightIndex = length - temp;
            }
            List<Tecton> newTectons = tectons.subList(leftIndex + 1, tectons.size()-1);
            tectons = tectons.subList(0, leftIndex);
            MushroomString newString = new MushroomString(rightIndex, mushroomBodies, newTectons);
            newTectons.forEach(t -> t.addNewString(newString));
            newTectons.forEach(t -> t.removeStrin(this));
            System.out.println("MushroomString cut in half");

        } else {
            if(leftIndex > rightIndex) {
                int temp = leftIndex;
                leftIndex = rightIndex;
                rightIndex = temp;
            }
            tectons.subList(leftIndex + 1, tectons.size()-1).forEach(t -> t.removeString(this));
            tectons = tectons.subList(0, leftIndex);
            System.out.println("MushroomString cut in one");
        }
    }

    /**
     * A gombaszál eltűnik a tektonról. A fonál kettészakad, ha csak a egyik oldalon van gombatest, akkor a másik eltűnik.
     * @param tecton, amiről eltűnik a gombaszál.
     */
    public void stringDisappear(Tecton tecton) {
        System.out.println("MushroomString disappear");
        if(mushroomBodies.size() >= 2) {
            System.out.println("MushroomString disappear in half");
        } else {
            System.out.println("MushroomString disappear in one");
        }
        //itt kéne vmi út felderítés vagymi az isten ez? sztem nagypn el van baszarintva a fonalak kezelése
    }

    /**
     * A gombaszál nő a paraméterként kapott tektonra.
     * @param tecton
     */
    public void growTo(Tecton tecton) {
        System.out.println("MushroomString grow to Tecton");
        tecton.stringGrowFrom(); //honnan? wtf
        if(tecton.haveSpore()) {
            System.out.println("Can grow more");
        } else {
            System.out.println("Can't grow more");
        }
    }

    /**
     * A gombaszál elágazik, ha a tecton, amire ágazik elég nagy.
     */
    public void branchOut() {
        System.out.println("MushroomString branch out");
        //honnan tudjuk, hogy melyik tektonnál ágazik el a szál?
        //honnan tudjuk h melyik tektonra ágazik el?

    }

    /**
     * Gombszál létrehozása.
     */
    public void createString() {
        //lehet tudni? nincs ilyen a szekvenci diagrammokon
    }

    /**
     * A gombaszál nő a paraméterként kapott tektonra.
     * @param tecton
     */
    public void grow(Tecton tecton) {
        //??? ilyen sincs
    }
}
