import java.awt.*;

public class MushroomBodyView {
    private MushroomBody model;
    private Graphics2D g;
    private transient Image image;

    public MushroomBodyView(MushroomBody model, Graphics2D g){
        this.model = model;
        //model.addObserver(this);
    }


    protected void paint(Graphics2D g) {
        //g.drawImage(image, xPos, yPos, null);
    }
}
