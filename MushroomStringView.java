import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MushroomStringView implements DrawableInterface{
    private MushroomString model;
    private transient Image image;
    private Color color;

    public MushroomStringView(MushroomString model, Color c) throws IOException {
        this.model = model;
        color = c;
        //TODO : color szerinti betöltés
        image = ImageIO.read(ClassLoader.getSystemResourceAsStream("insect.png")).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    }
}
