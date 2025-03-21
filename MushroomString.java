import java.util.List;


public class MushroomString {
    private int length;
    private List<MushroomBody> mushroomBodies;

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
    public void cut() {
        System.out.println("MushroomString cut");
        if(mushroomBodies.size() >= 2) {
            System.out.println("MushroomString cut in half");
        } else {
            System.out.println("MushroomString cut in one");
        }
        //Honnan tudjuk melyik tektonnál vágja el a rovar a szálat?
        //Mi alapján van a szál hossza?
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
