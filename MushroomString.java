import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MushroomString {
    private int length;
    private List<MushroomBody> mushroomBodies;
    private List<Tecton> tectons;
    private List<Map<Tecton, Tecton>> branches; //Key: from, Value: to

    private void removeTecton(Tecton tecton) {
        tectons.remove(tecton);
        branches.remove(tecton);
    }

    public MushroomString(int length, List<MushroomBody> mushroomBodies, List<Tecton> tectons, List<Map<Tecton, Tecton>> branches) {
        this.length = length;
        this.mushroomBodies = mushroomBodies;
        this.tectons = tectons;
        this.branches = branches;
    }

    public MushroomString(MushroomBody mushroomBody, Tecton tecton) {
        length = 1;
        this.mushroomBodies = new ArrayList<MushroomBody>();
        this.tectons = new ArrayList<Tecton>();
        this.branches = new ArrayList<Map<Tecton, Tecton>>();
        this.mushroomBodies.add(mushroomBody);
        this.tectons.add(tecton);
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
     * @param t1 - Az egyik tecton, amelyik a vágás egyik pontja.
     * @param t2 - A másik tecton, amelyik a vágás másik pontja.
     *           A tectonoknak szomszédosnak kell lenniük.
     *           A vágás a két tekton között történik
     *
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Is there mushroomBody on both end?[y/n]: ");
        String input = scanner.nextLine();
        int index = tectons.indexOf(tecton);
        if(input.equals("y") && mushroomBodies.size() >= 2) {
            List<Tecton> newTectons = tectons.subList(index + 1, tectons.size()-1);
            tectons = tectons.subList(0, index);
            MushroomString newString = new MushroomString(length - index, mushroomBodies, newTectons);
            newTectons.forEach(t -> t.addNewString(newString));
            newTectons.forEach(t -> t.removeString(this));
            tecton.removeString(this);
            System.out.println("MushroomString disappear");
        } else {
            tectons.subList(index + 1, tectons.size()-1).forEach(t -> t.removeString(this));
            tecton.removeString(this);
            System.out.println("MushroomString disappear");
        }
    }

    /**
     * A gombaszál nő a paraméterként kapott tektonra.
     * @param toGrow - A tekton, amire a gombaszál nőni fog.
     * @param from - A tekton, ahonnan a gombaszál nőni fog.
     *             A két tektonnak szomszédosnak kell lennie.
     */
    public void growTo(Tecton toGrow, Tecton from) {
        if(from.neighbourDistance(toGrow) != 1) {
            System.out.println("Can't grow MushroomString, tectons are not neighbours");
            return;
        }
        toGrow.addNewString(this);
        tectons.add(toGrow);
        length++;

        if(toGrow.haveSpore()) {
            System.out.println("Can grow more");
        } else {
            System.out.println("Can't grow more");
        }
    }

    /**
     * A gombaszál elágazik, ha a tecton, amire ágazik elég nagy.
     * @param toBranch - A tekton, amire a gombaszál ágazni fog.
     * @param from - A tekton, ahonnan a gombaszál ágazni fog.
     *                 A két tektonnak szomszédosnak kell lennie.
     *                 A toBranch tecton méretének legalább 2-nek kell lennie.
     *
     */
    public void branchOut(Tecton toBranch, Tecton from) {
        if(from.neighbourDistance(toBranch) != 1) {
            System.out.println("Can't branch MushroomString, tectons are not neighbours");
            return;
        }
        if(toBranch.canBranch()) {
            MushroomString newString = new MushroomString(length++, mushroomBodies, List.of(toBranch), List.of(Map.of(toBranch, from)));
            toBranch.addNewString(newString);
            tectons.add(toBranch);
            System.out.println("MushroomString branched out");
        } else {
            System.out.println("Can't branch MushroomString, tecton is not big enough");
        }

    }

}
