import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.IOException;

public class MushroomStringView implements DrawableInterface{
    private MushroomString model;
    private transient Image image;
    private TeamColor color;

    public MushroomStringView(MushroomString model, TeamColor c) throws IOException {
        this.model = model;
        color = c;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<Tecton> tectons = model.getTectons();
        for (int i = 0; i < tectons.size()-1; i++) {
            g2d.
            g2d.drawLine(tectons.get(i).getPosition()[0], tectons.get(i).getPosition()[0], tectons.get(i).getPosition()[0], tectons.get(i).getPosition()[0]);
        }
    }
}
