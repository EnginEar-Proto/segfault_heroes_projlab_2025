import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MushroomString {
    private String id;
    private int length;
    private List<MushroomBody> mushroomBodies;
    private List<Tecton> tectons;
    private MushroomString parentString;
    private List<MushroomString> childrenStrings;
    private int timeLeft = -1;

    public MushroomString(String id, MushroomBody mushroomBody, Tecton tecton) {
        length = 1;
        this.mushroomBodies = new ArrayList<MushroomBody>();
        this.tectons = new ArrayList<Tecton>();
        this.childrenStrings = new ArrayList<MushroomString>();
        this.mushroomBodies.add(mushroomBody);
        this.tectons.add(tecton);
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getLength(){
        return this.length;
    }

    public List<MushroomBody> getMushroomBodies(){
        return this.mushroomBodies;
    }

    public List<Tecton> getTectons(){
        return this.tectons;
    }

    public MushroomString(String id) {
        this.id = id;
        length = 0;
        this.mushroomBodies = new ArrayList<MushroomBody>();
        this.tectons = new ArrayList<Tecton>();
        this.childrenStrings = new ArrayList<MushroomString>();

    }

    public MushroomString(String id, MushroomBody mushroomBody, List<Tecton> tectons, MushroomString parentString) {
        length = 0;
        this.mushroomBodies = new ArrayList<MushroomBody>();
        this.tectons = new ArrayList<Tecton>();
        this.childrenStrings = new ArrayList<MushroomString>();
        this.parentString = parentString;
        this.mushroomBodies.add(mushroomBody);
        this.tectons.addAll(tectons);
        this.id = id;
    }
    /**
     * Tecton felvétele a gombafonal utjára.
     * <i>A paraméterül kapott tekton listájához is hozzáadja magát</i>
     * @param tecton
     */
    void addTecton(Tecton tecton) {
        tectons.add(tecton);
        tecton.addNewString(this);
    }

    /**
     * Tecton eltávolítása a gombafonal utjáról.
     * <i>A paraméterül kapott tecton listájából is eltávolítja</i>
     * @param tecton
     */
    private void removeTecton(Tecton tecton) {
        tectons.remove(tecton);
        tecton.removeString(this);
    }

    /**
     * Gombatest hozzáadása a gombaszálhoz.
     * <i>A gombatest listájához is hozzáadja magát</i>
     * @param mushroomBody
     */
    public void addMushroomBody(MushroomBody mushroomBody) {
        mushroomBodies.add(mushroomBody);
        mushroomBody.addString(this);
        timeLeft = -1;
    }
    /**
     * Gombatest eltávolítása a gombaszálról.
     * <i>A gombatest listájából is eltávolítja</i>
     * @param mushroomBody
     */
    public void removeMushroomBody(MushroomBody mushroomBody) {
        mushroomBodies.remove(mushroomBody);
        mushroomBody.removeString(this);
        if(mushroomBodies.isEmpty()) {
            if(tectons.stream().anyMatch(Tecton::getSaveMushroomString)) return;
            timeLeft = 2;
        }
    }

    /**
     * Elágazáshoz leszármazott ág hozzáadása.
     * @param child
     */
    public void addChild(MushroomString child) {
        childrenStrings.add(child);
        child.parentString = this; //ennek nem kéne jónak lennie...
    }
    /**
     * Elágazáshoz leszármazott ág eltávolítása és a szülő gombaszál nullázása.
     * @param child
     */
    public void removeChild(MushroomString child) {
        childrenStrings.remove(child);
        child.parentString = null; //ennek nem kéne jónak lennie...
    }


    /**
     * Elvágja a gombaszálat.
     * A gombaszálból 2 rész marad, ha mindkét oldalt van gombatest ami táblálja, egyébként a gombaszál egyik fele törlődik.
     * @param t1 - Az egyik tecton, amelyik a vágás egyik pontja.
     * @param t2 - A másik tecton, amelyik a vágás másik pontja.
     *           A tectonoknak szomszédosnak kell lenniük.
     *           A vágás a két tekton között történik
     *
     * @return -1, ha a tectonok nem szomszédosak.
     */
    public int cut(Tecton t1, Tecton t2) {
        if(t1.neighbourDistance(t2) != 1) {
            return -1;
        }
        int index1 = tectons.indexOf(t1);
        int index2 = tectons.indexOf(t2);
        if(index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        MushroomString newString = new MushroomString("_c");
        for(int i = index2; i < tectons.size(); i++) {
            final Tecton tecton = tectons.get(i);

            newString.addTecton(tecton);
            tecton.removeString(this);

            MushroomBody bodyOnTecton = tecton.getMushroomBody();
            if(bodyOnTecton != null) {
                if(mushroomBodies.contains(bodyOnTecton)) {
                    newString.addMushroomBody(bodyOnTecton);
                    mushroomBodies.remove(bodyOnTecton);
                    bodyOnTecton.removeString(this);
                    bodyOnTecton.addString(newString);
                }
            }

            MushroomString child = childrenStrings.stream().filter(c -> c.tectons.contains(tecton)).findFirst().orElse(null);
            if(child != null)
            {
                newString.addChild(child);
                child.parentString = newString;
                child.removeTecton(tecton);
            }

        }

        this.length = mushroomBodies.size();
        newString.length = newString.mushroomBodies.size();

        for(int i = index2; i < tectons.size(); i++) {
            tectons.removeLast();
        }

        if(mushroomBodies.isEmpty()) timeLeft = 2;
        if(newString.mushroomBodies.isEmpty()) newString.timeLeft = 2;

        return newString.mushroomBodies.size();
    }

    /**
     * A gombaszál eltűnik a tektonról. A fonál kettészakad, ha csak a egyik oldalon van gombatest, akkor a másik eltűnik.
     * @param tecton, amiről eltűnik a gombaszál.
     */
    public int stringDisappear(Tecton tecton) {
        if(!tectons.contains(tecton)) return -1;
        int index = tectons.indexOf(tecton);
        Tecton before = index == 0 ? null : tectons.get(index - 1);
        Tecton after = index == tectons.size() - 1 ? null : tectons.get(index + 1);

        if(before != null) {
            this.cut(tecton, before);
        }
        if(after != null) {
            this.cut(tecton, after);
        }

        tecton.removeString(this);
        for(MushroomBody body : mushroomBodies) {
            body.removeString(this);
        }
        mushroomBodies.clear();
        tectons.clear();
        childrenStrings.clear();
        return 0;
    }

    /**
     * A gombafonal elhal, ha lejár az ideje.
     * @return -1, ha a gombafonal nem haldoklik.
     * @return 0, ha a gombafonal elhal.
     * @return a gombafonal hátralévő ideje, ha még nem halt el.
     */
    public int stringDisappear() {
        if(timeLeft == -1) return -1;
        if(timeLeft > 0) {
            timeLeft--;
            return timeLeft;
        }
        for(Tecton tecton : tectons) {
            tecton.removeString(this);
        }
        for(MushroomBody body : mushroomBodies) {
            body.removeString(this);
        }
        mushroomBodies.clear();
        tectons.clear();
        childrenStrings.clear();
        return 0;
    }

    /**
     * A gombaszál nő a paraméterként kapott tektonra.
     * @param toGrow - A tekton, amire a gombaszál nőni fog.
     * @param from - A tekton, ahonnan a gombaszál nőni fog.
     *             A két tektonnak szomszédosnak kell lennie.
     */
    public int growTo(Tecton toGrow, Tecton from) {

        //TODO: grow on same tekton
        if(from.neighbourDistance(toGrow) != 1 && toGrow != from) {
            return -1;
        }
        if(/*tectons.indexOf(from) != 0 && tectons.indexOf(from) != tectons.size() - 1 && toGrow != from*/false) {
            return -1;
        }

        toGrow.addNewString(this);
        from.addNewString(this);
        if(!tectons.contains(toGrow)) tectons.add(toGrow);
        if(!tectons.contains(from)) tectons.add(from);
        length++;

        return tectons.size();
    }

    /**
     * A gombaszál elágazik, ha a tecton, amire ágazik elég nagy.
     * @param tecton - A tekton, ahonnan a gombaszál ágazni fog.
     *                 A két tektonnak szomszédosnak kell lennie.
     *                 A toBranch tecton méretének legalább 2-nek kell lennie.
     *
     */
    public void branchOut(Tecton tecton) {
        MushroomString newString = new MushroomString(this.id + "_b", null, tectons, this);
        tecton.addNewString(newString);
    }

    /**
     * A fonal megeszi a parméterül kapott tekton rovarait.
     * Ha még nincs gombatest a tektonon, akkor létrehoz egyet.
     * @param tecton
     */
    public boolean eatParalyzedInsects(Tecton tecton) {
        boolean foundParalyzedInsect = false;
        if(tectons.contains(tecton)) {
            for(Insect insect : tecton.getInsects()) {
                if(insect.getAbility() == Ability.PARALYZING) {
                    Insecter insecter = insect.getInsecter();
                    insecter.insectEatenByString(insect);

                    tecton.removeInsect(insect);
                    insect.setTecton(null);
                    //mi legyen a rovarral? hogyan remove stb..?

                    if(tecton.getMushroomBody() == null) {
                        MushroomBody body = new MushroomBody(id + "body", tecton);
                        tecton.setMushroomBody(body);
                    }
                    foundParalyzedInsect = true;
                }
            }
        }
        return foundParalyzedInsect;
    }


}
