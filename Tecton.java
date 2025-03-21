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
    private ArrayList<MushroomString> strings; /*Itt majd figyrelni, hogy a mi String osztályunk legyen!*/
    
    /**
     * Ezzel a tektonnal szomszédos tektonok listája.
    */
    private ArrayList<Tecton> neighbours;
    
    /**
     * A Tektonon aktuális mérete. Ez az érték nem lehet kisebb mint egy.
    */
    private int size;

    public Tecton(int size){
        this.size = size;
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
     * @return Visszatér egy logikai értékkel, amely igaz ha a tektonon elfér még egy fonal. Hamis egyébként.
     */
    public boolean canBranch(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Can string branch?[y/n]: ");
        String input = scanner.nextLine();
        if(input.equals("y"))
            return true;
        else if(input.equals("n"))
            return false;
        else
            System.out.println("Given char is not valid");
        return false;
    }
    /**
     * Meghatározza a paraméterként kapott tekton távolságát attól a tektontól, amelyen mmeghívtuk a
     * függvényt.
     * @param t - Az a tekton amelynek a távolságát mérjük ettpl a tektontól.
     * @return A paraméterként kapott tekton távolsága ettől a tektontól.
     */
    public int neighbourDistance(Tecton t){
        //Bálint: Itt lehet jobb lenne csak bekérni hogy hanyadik szomszéd, mert igy szar lesz a tesztesetet megcsinálni
        boolean founded = false;

        for(int i = 0; i < neighbours.size(); i++){
            if(neighbours.get(i).equals(t)) {
                founded = true;
                return 1;
            }
        }
        if(!founded){
            for(int i = 0; i < neighbours.size(); i++){
                for(int j = 0; j < neighbours.get(i).neighbours.size(); j++){
                    if(neighbours.get(i).neighbours.get(j).equals(t)){
                        founded = true;
                        return 2;
                    }
                }
            }
        }

        if(!founded){
            for(int i = 0; i < neighbours.size(); i++){
                for(int j = 0; j < neighbours.get(i).neighbours.size(); j++){
                    for(int k = 0; k < neighbours.get(i).neighbours.get(j).neighbours.size(); k++){
                        if(neighbours.get(i).neighbours.get(j).neighbours.get(k).equals(t)){
                            founded = true;
                            return 3;
                        }
                    }
                }
            }
        }

        return 4;   //ha 3-nál messzebb van, akkor úgyis mindegy
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
        MushroomBody newBody = new MushroomBody();
        this.mushroomBody = newBody;

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
    }
    /**
     * A tekton tördelése.
     * @param size - A tekton új mérete 
     */
    public void Break(int size){
        removeStrings();
        int oldSize = strings.size();
        setSize(size);
        Tecton newTecton = new Tecton(oldSize - this.size); //Bálint: Szerintem itt így logikus
        //Bálint: Itt honnan tudjuk, hogy kik lesznek a szomszédai?
        //Bálint: Ez átmeneti de ezt tényleg nem tudom, majd beszéljük meg
        ArrayList<Tecton> newNeighbours = new ArrayList<>();
        newTecton.setNeighbours(neighbours);

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
        //Bálint: Itt is controller nélkül vizsgálgatni mindent nagyon szar lenne, úgyhogy én így csinálnám
        Scanner scanner = new Scanner(System.in);
        System.out.println("Can insect move to t2?[y/n]: ");
        String input = scanner.nextLine();
        if(input.equals("y"))
            return true;
        else if(input.equals("n"))
            return false;
        else
            System.out.println("Given char is not valid");
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
        else {
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
     * A rovar elhelyezése a játék megkezdésekor azon a tektonon, amelyre meghívták a függvényt.
     * @param i - A rovar amely elhelyezésre kerül.
     */
    public void setInsect(Insect i){
        insects.add(i);
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
        neighbours.addAll(tectons);
    }
}
