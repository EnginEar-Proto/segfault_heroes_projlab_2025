import java.util.List;
import java.util.Map;

/**
 * Skeleton osztály, amely a test use case-eket inicializálja.
*/
public class Skeleton {
    public void JatekInditas(){
        Mushroomer m1 = new Mushroomer();
        Insecter i1 = new Insecter();
        Tecton t1 = new Tecton(2, false);
        Tecton t2 = new Tecton(2, false);
        Team tm1 = new Team();
        tm1.setPlayers(m1, i1);
        tm1.setPositions(t1, t2);
    }
    
    //#region Gombafonal növesztés
    public void GombatestbolFonal(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        MushroomBody g1 = new MushroomBody(t1);
        MushroomString f1 = new MushroomString(g1, t1);
        g1.createNewString();

    }
    public void FonalbolFonalNovesztes(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        Tecton t2 = new Tecton(2, false);
        MushroomString f1 = new MushroomString(null, t1);
        f1.growTo(t2, t1);
    }
    public void FonalbolFonalNovesztesSporaval(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        MushroomString f1 = new MushroomString(null, t1);
        Tecton t2 = new Tecton(2, false);
        Tecton t3 = new Tecton(2, false);
        f1.growTo(t2, t1);
    }
    //#endregion

    public void FonalAgaztatas(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        Tecton t2 = new Tecton(2, false);
        MushroomString f1 = new MushroomString(null, t1);
        f1.branchOut(t2, t1);
    }

    public void SporaSzoras(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        MushroomBody g1 = new MushroomBody(t1);
        t1.setMushroomBody(g1);
        Tecton t2 = new Tecton(2, false);
        Spore s1 = new Spore(2, Ability.NORMAL);
        g1.scatter(t2);
    }

    public void GombatestNovesztes(){
        Mushroomer m1 = new Mushroomer();
        Tecton t1 = new Tecton(2, false);
        MushroomString f1 = new MushroomString(null, t1);
        t1.growBody();
    }

    public void SporaEves(){
        Insecter i1 = new Insecter();
        Insect r1 = new Insect(0, Ability.NORMAL, i1);
        i1.addInsect(r1);
        Tecton t1 = new Tecton(2, false);
        Spore s1 = new Spore(1, Ability.NORMAL);
        t1.scatterSpore(s1);
        r1.eat(s1);
    }

    public void FonalonMozgas(){
        Insecter i1 = new Insecter();
        Insect r1 = new Insect(0, Ability.NORMAL, i1);
        i1.addInsect(r1);
        Tecton t1 = new Tecton(2, false);
        t1.setInsect(r1);
        Tecton t2 = new Tecton(2, false);
        t1.setNeighbours(List.of(t2));
        t2.setNeighbours(List.of(t1));
        r1.moveTo(t2);
    }

    public void FonalVagas(){
        Insecter i1 = new Insecter();
        Insect r1 = new Insect(0, Ability.NORMAL, i1);
        i1.addInsect(r1);
        Tecton t1 = new Tecton(2, false);
        t1.setInsect(r1);
        Tecton t2 = new Tecton(2, false);
        t1.setNeighbours(List.of(t2));
        t2.setNeighbours(List.of(t1));
        MushroomBody g1 = new MushroomBody(t1);
        MushroomString f1 = new MushroomString(g1, t1);
        r1.sabotageString(f1, t1, t2);
    }

    public void LehetsegesTektonTores(){
        Tecton t1 = new Tecton(2, false);
        t1.Break(1);
    }

    public void SikertelenTektonTores(){
        Tecton t1 = new Tecton(1, false);
        t1.Break(1);
    }

    public void UjKorInditasa(){
        Mushroomer m1 = new Mushroomer();
        Insecter i1 = new Insecter();
        Tecton t1 = new Tecton(2, false);
        Tecton t2 = new Tecton(2, false);
        Team tm1 = new Team();
        tm1.setPlayers(m1, i1);
        tm1.setPositions(t1, t2);
    }
}

/*
 * Test use case-ek esestén mindig az utolsó sor az ami elindítja a folyamatot.
 * Az előtt csak a szükséges objektumok inicializáslása történik.
*/