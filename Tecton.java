import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <h2>Tekton</h2>
 * A tekton a játék egy pályaeleme, a játéktér tektonokból áll.
 * Ezeken más objektumok (gombatestek, rovarok, spórák, fonalak) helyezkedhetnek el. 
 * A tekton felelőssége ezen objektumok kezelése.
 * <h4>Összeköttetés</h4>
 * Két tekton között vezethet gombafonál, amelyeken keresztül a rovarok mozoghatnak.
 * <h4>Törés</h4>
 * A tektonok kettétörhetnek, ami a játék menetét befolyásolja, 
 * és ilyenkor az eddig rajta lévő fonalak megszűnnek, 
 * ellenben az esetleg rajta lévő gombatest az egyik tektonon marad.
*/
public class Tecton {
    /**
     * A Tektonon tartozdkodó rovarok.
    */
    private ArrayList<Insect> insects;

    /**
     * A Tekton második szomszédainak listája
     */
    private ArrayList<Tecton> secondNeighbours;
    /**
     * A Tekton harmadik szomszédainak listája
     */
    private ArrayList<Tecton> thirdNeighbours;

    /**
     * Igaz érték esetén a tektonon lévő gombafonalak megmaradnak, akkor is, ha nem tartozik hozzájuk gombatest.
     */
    private boolean saveMushroomString;

    /**
     * Amennyiben a tektonon van gombatest, akkor az ebben a tagváltozóban van eltárolva.
    */
    private MushroomBody mushroomBody;

    /**
     * A Tektononlevő spórák tárolására használt lista.
    */
    private ArrayList<Spore> spores;

    /**
     * A Tektonon átfutó gombafonalak listája.
    */
    private ArrayList<MushroomString> strings; 
    
    /**
     * Ezzel a tektonnal szomszédos tektonok listája.
    */
    private ArrayList<Tecton> neighbours;
    
    /**
     * A Tektonon aktuális mérete. Ez az érték nem lehet kisebb mint egy.
    */
    private int size;

    private int[] position = new int[2];

    private String id;

    public Tecton(String id, int size, boolean saveMushroomString, int x, int y) {
        this.id = id;
        insects = new ArrayList<>();
        spores = new ArrayList<>();
        strings = new ArrayList<>();
        neighbours = new ArrayList<>();
        position[0] = x;
        position[1] = y;
        this.size = size;
        this.saveMushroomString = saveMushroomString;
    }

    public String getId() {
        return id;
    }

    public boolean getSaveMushroomString() {
        return saveMushroomString;
    }

    /**
     * A tektonon levő fonál elvágása.
     * @param s - A fonál ami elvágásra fog kerülni.
     */
    public void cutString(MushroomString s){
        strings.remove(s);
    }
    /**
     * Megadja, hogy az adott tektonra felfér még egy gombafonal.
     * Ammenyiben a tekton mérete 2, akkor több fonal is tud áthaladni a tektonon.
     * Ha csak 1-es méretű, akkor csak egy 
     * @return Visszatér egy logikai értékkel, amely igaz ha a tektonon elfér még egy fonal. Hamis egyébként.
     */
    public boolean canBranch(){
        if(size > 1)
            return true;
        return false;
    }
    /**
     * Meghatározza a paraméterként kapott tekton távolságát attól a tektontól, amelyen mmeghívtuk a
     * függvényt. Megnézi, hogy az előre definiált listák, amelyek a második és harmadik szomszédokat tárolják üresek-e,
     * hiszen ha nem, akkor nem kell végigkeresni a szomszédok szomszédait is.
     * @param t - Az a tekton amelynek a távolságát mérjük ettpl a tektontól.
     * @return A paraméterként kapott tekton távolsága ettől a tektontól.
     */
    public int neighbourDistance(Tecton t){
        //Bálint: Itt lehet jobb lenne csak bekérni hogy hanyadik szomszéd, mert igy szar lesz a tesztesetet megcsinálni

        for(int i = 0; i < neighbours.size(); i++){
            if(neighbours.get(i).equals(t))
                return 1;
        }

        if(!secondNeighbours.isEmpty()){
            for(int i = 0; i < secondNeighbours.size(); i++){
                if(secondNeighbours.get(i).equals(t))
                    return 2;
            }
        }else{
            for(int i = 0; i < neighbours.size(); i++){
                for(int j = 0; j < neighbours.get(i).neighbours.size(); j++){
                    secondNeighbours.add(neighbours.get(i).neighbours.get(j));
                    if(neighbours.get(i).neighbours.get(j).equals(t))
                        return 2;
                }
            }
        }

        if(!thirdNeighbours.isEmpty()){
            for(int i = 0; i < thirdNeighbours.size(); i++){
                if(thirdNeighbours.get(i).equals(t))
                    return 3;
            }
        }else{
            for(int i = 0; i < neighbours.size(); i++){
                for(int j = 0; j < neighbours.get(i).neighbours.size(); j++){
                    for(int a = 0; a < neighbours.get(i).neighbours.get(j).neighbours.size(); a++){
                        thirdNeighbours.add(neighbours.get(i).neighbours.get(j).neighbours.get(a));
                        if(neighbours.get(i).neighbours.get(j).neighbours.get(a).equals(t))
                            return 3;
                    }
                }
            }
        }

        return 4;

    }
    /**
     * A paraméterként kapott spórát elhelyezi azon a tektonon, amelyen a függvényt meghívták.
     * @param s - A spóra amit erre a tektonra szórunk.
    */
    public void scatterSpore(Spore s){
        spores.add(s);
    }
    /**
     * Új gombatestet növeszt arra a tektonra, amelyen a függvényt meghívták.
     */
    public void growBody(){
        if(this.mushroomBody != null){
            MushroomBody newBody = new MushroomBody(this);
            this.mushroomBody = newBody;
            System.out.print("New mushroom body in this tecton");
        }else{
            System.out.print("There is something on this tecton");
        }


    }
    public void removeString(MushroomString f){
        strings.remove(f);
    }
    /**
     * Megadja, hogy van spóra azon a tektonon, amelyre a függvényt meghívták.
     * @return Logikai érték, amely igaz, ha a tektonon helyezkedik el spóra. Hamis egyébként.
     */
    public boolean haveSpore(){
        return !(spores.isEmpty());
    }
    /**
     * Az tektonról törli az összes gombafonalat.
    */
    public void removeStrings(){
        strings = new ArrayList<>();
        System.out.print("All strings removed");
    }
    /**
     * A tekton tördelése.
     * @param size - A tekton új mérete 
     */
    public void Break(int size){
        if((this.size - size) > 0) {
            removeStrings();
            int oldSize = strings.size();
            setSize(size);
            int x = 0, y = 0;
            if(oldSize == 3){
                x = position[0];
                y = position[1] - 1;
            }
            Tecton newTecton = new Tecton(this.id + " broken", oldSize - this.size, false, x, y);
            this.setNeighbours(neighbours); //Itt ugy változik az is, hogy kik a saját szomszédai
            newTecton.setNeighbours(neighbours);
        }else
            System.out.print("The tectonic plate can't break");

    }
    /**
     * A tekton új méretének a beállítására szolgál.
     * A tekton létrehozásakor és a tekton tördelésekor kell meghívódjon.
    */
    public void setSize(int newSize){
        //Honnan tudja, hogy most mekokora kell legyen, ha nem kapta meg paraméterben?
        //Bálint: kap paramétert, szekvencián úgy van
        this.size = newSize;
    }
    /**
     * Visszaadja, hogy egy rovar átmehet e a paraméterül kapott tektonra.
     * @param t - A tekton amelyre a rovar át akar kellni erről a tektonról.
     * @return Logikai érték, amely igaz, ha a rovar képes átkelni a paraméterként kapott tektonra.
     * Hamis egyébként
     */
    public boolean canMoveTo(Tecton t){
        //Meg kell nézni, hogy szomszédos-e a két tekton
        if(neighbours.contains(t)) {
            //Meg kell nézni, hogy van-e ugyan az a fonal a két tektonon, mivel ha van,akkor át tud  menni a rovar
            for (int i = 0; i < strings.size(); i++) {
                for (int j = 0; j < t.strings.size(); j++) {
                    if (strings.get(i) == t.strings.get(j))
                        return true;
                }
            }
        }
        return false;
    }
    /**
     * A paraméterül kapott rovar átszáll arra a tekntora amelyre a függvényt meghívták.
     * @param i - A rovar amely erre a tektonra repül.
     */
    public void setBugPosition(Insect i){
        if(insects.contains(i)){
            return;
        }
        else if(i != null){
            insects.add(i);
        }
        /*TODO:Ellenőrizzük, hogy nem-e ugyanarra tektonra száll vissza a rovar.
         * Csak ezt követően adjuk hozzá a listához.
        */
    }
    /**
     * A paraméterül kapott gombatestet elhelyezi azon a tektonon amelyre a függvényt meghívták.
     * @param mb - Az elhelyezésre kerülő gombatest
     */
    public void setMushroomBody(MushroomBody mb){
        mushroomBody = mb;
    }
    /**
     * Visszaadja a tektonon lévő gombatestet.
     * @return A tektonon lévő gombatest.
     */
    public MushroomBody getMushroomBody(){
        return mushroomBody;
    }
    /**
     * A rovar elhelyezése a játék megkezdésekor azon a tektonon, amelyre meghívták a függvényt.
     * @param i - A rovar amely elhelyezésre kerül.
     */
    public void setInsect(Insect i){
        insects.add(i);
    }
    /**
     * A tektonon lévő rovarok listáját adja vissza.
     * @return A tektonon lévő rovarok listája.
     */
    public List<Insect> getInsects(){
        return insects;
    }
    /**
     * A tektonon lévő fonalak listáját adja vissza.
     * @return A tektonon lévő fonalak listája.
     */
    public List<MushroomString> getStrings(){
        return strings;
    }
    /**
     * A paraméterként kapott fonal átterjed arra a tektonra amelyre a függvényt meghívták.
     * @param s - A fonal, amely átterjed a tektonra
     */
    public void addNewString(MushroomString s){
        if(strings.contains(s))
            return;
        strings.add(s);
    }
    /**
     * A paraméterben átadott listában szereplő tektonok szomszédosak lesznek azzal a tektonnal,
     * amelyre meghíták a függvényt. Az itt szereplő tektonok még nem feltétlenül vannak fonalakkal összekötve.
     * @param tectons - Szomszédos tektonokból álló lista.
     */
    public void setNeighbours(List<Tecton> tectons){
        for(int i = 0; i < tectons.size(); i++){
            if(size == 2 && (tectons.get(i).position[1] != position[1] + 2 || tectons.get(i).position[1] != position[1] + 3)){
                neighbours.add(tectons.get(i));
            }
            if(size == 1 && (tectons.get(i).position[0] != position[0] + 2 || tectons.get(i).position[0] != position[0] + 3)){
                neighbours.add(tectons.get(i));
            }
        }
    }
}
