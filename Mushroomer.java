import java.util.ArrayList;

public class Mushroomer {
    private ArrayList<MushroomBody> mushroomBodies;

    public Mushroomer() {
        mushroomBodies = new ArrayList<MushroomBody>();
    }

    public void addMushroomBody(MushroomBody m) {
        mushroomBodies.add(m);
    }

    public MushroomBody getMushroomBody(int index) {
        return mushroomBodies.indexOf(index) == null ? null : mushroomBodies.get(index);
    }
}
