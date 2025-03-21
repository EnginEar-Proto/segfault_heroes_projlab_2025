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
}

/*
 * Test use case-ek esestén mindig az utolsó sor az ami elindítja a folyamatot.
 * Az előtt csak a szükséges objektumok inicializáslása történik.
*/