import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<String> strings; /*Itt majd figyrelni, hogy a mi String osztályunk legyen!*/
    
    /**
     * Ezzel a tektonnal szomszédos tektonok listája.
    */
    private ArrayList<Tecton> neighbours;
    
    /**
     * A Tektonon aktuális mérete. Ez az érték nem lehet kisebb mint egy.
    */
    private int size;

    public Tecton(){

    }

    /**
     * A tektonon levő fonál elvágása.
     * @param s - A fonál ami elvágásra fog kerülni.
     */
    public void cutString(String s){

    }
    /**
     * Megadja, hogy az adott tektonra felfér még egy gombafonal.
     * @return Visszatér egy logikai értékkel, amely igaz ha a tektonon elfér még egy fonal. Hamis egyébként.
     */
    public boolean canBranch(){

    }
    /**
     * Meghatározza a paraméterklnt kapott tekton távolságát attól a tektontól, amelyen mmeghívtuk a 
     * függvényt.
     * @param t - Az a tekton amelynek a távolságát mérjük ettpl a tektontól.
     * @return A paraméterként kapott tekton távolsága ettől a tektontól.
     */
    public int neighbourDistance(Tecton t){

    }
    /**
     * A paraméterként kapott spórát elhelyezi azon a tektonon, amelyen a függvényt meghívták.
     * @param s - A spóra amit erre a tektonra szórunk.
    */
    public void scatterSpore(Spore s){

    }
    /**
     * Új gombatestet növeszt arra a tektonra, amelyen a függvényt meghívták.
     */
    public void growBody(){

    }
    /**
     * Megadja, hogy van spóra azon a tektonon, amelyre a függvényt meghívták.
     * @return Logikai érték, amely igaz, ha a tektonon helyezkedik el spóra. Hamis egyébként.
     */
    public boolean haveSpore(){

    }
    /**
     * Az tektonról törli az összes gombafonalat.
    */
    public void removeStrings(){

    }
    /**
     * A tekton tördelése.
     * @param size - A tekton új mérete 
     */
    public void Break(int size){

    }
    /**
     * A tekton új méretének a beállítására szolgál.
     * A tekton létrehozásakor és a tekton tördelésekor kell meghívódjon.
    */
    public void setSize(){

    }
    /**
     * Visszaadja, hogy egy rovar átmehet e a paraméterül kapott tektonra.
     * @param t - A tekton amelyre a rovar át akar kellni erről a tektonról.
     * @return Logikai érték, amely igaz, ha a rovar képes átkelni a paraméterként kapott tektonra.
     * Hamis egyébként
     */
    public boolean canMoveTo(Tecton t){
        
    }
    /**
     * A paraméterül kapott rovar átszáll arra a tekntora amelyre a függvényt meghívták.
     * @param i - A rovar amely erre a tektonra repül.
     */
    public void setBugPosition(Insect i){

    }
    /**
     * A paraméterül kapott gombatestet elhelyezi azon a tektonon amelyre a függvényt meghívták.
     * @param mb - Az elhelyezésre kerülő gombatest
     */
    public void setMushroomBody(MushroomBody mb){

    }
    /**
     * A rovar elhelyezése a játék megkezdésekor azon a tektonon, amelyre meghívták a függvényt.
     * @param i - A rovar amely elhelyezésre kerül.
     */
    public void setInsect(Insect i){

    }
    /**
     * A paraméterként kapott fonal átterjed arra a tektonra amelyre a függvényt meghívták.
     * @param s - A fonal, amely átterjed a tektonra
     */
    public void addNewString(String s){

    }
    /**
     * A paraméterben átadott listában szereplő tektonok szomszédosak lesznek azzal a tektonnal,
     * amelyre meghíták a függvényt. Az itt szereplő tektonok még nem feltétlenül vannak fonalakkal összekötve.
     * @param tectons - Szomszédos tektonokból álló lista.
     */
    public void setNeighbours(List<Tecton> tectons){
        neighbours.addAll(tectons);   
    }
}
