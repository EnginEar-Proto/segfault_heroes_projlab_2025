/**
 * Skeleton osztály, amely a test use case-eket inicializálja.
*/
public class Skeleton {
    public void JatekInditas(){
        Mushroomer m1 = new Mushroomer();
        Insecter i1 = new Insecter();
        Tecton t1 = new Tecton(2);
        Tecton t2 = new Tecton(2);
        Team tm1 = new Team();
        tm1.setPlayers(m1, i1);
        tm1.setPositions(t1, t2);
    }
    
    //#region Gombafonal növesztés
    public void GombatestbolFonal(){
        Mushroomer m1 = new Mushroomer();
        MushroomBody g1 = new MushroomBody();
        //Gergő: Ez nem a mi string osztályunk. Javasolok egy ShroomString-re való átnevezést.
        String f1 = new String(); 
        Tecton t1 = new Tecton(2);
        g1.createNewString();
        //Gergő: Ez itt eddig tud haladni, mert a további részt a MushroomBody osztályban kell megvalósítani.
    
    }
    public void FonalbolFonalNovesztes(){
        Mushroomer m1 = new Mushroomer();
        String f1 = new String();
        Tecton t1 = new Tecton(2);
        Tecton t2 = new Tecton(2);
        f1.growTo(t2);
        //Gergő: Ez itt eddig tud haladni, mert a további részt a ShroomString osztályban kell megvalósítani.
    }
    public void FonalbolFonalNovesztesSporaval(){
        Mushroomer m1 = new Mushroomer();
        String f1 = new String();
        Tecton t1 = new Tecton(2);
        Tecton t2 = new Tecton(2);
        Tecton t3 = new Tecton(2);
        f1.growTo(t2);
    }
    //#endregion
}

/*
 * Test use case-ek esestén mindig az utolsó sor az ami elindítja a folyamatot.
 * Az előtt csak a szükséges objektumok inicializáslása történik.
*/