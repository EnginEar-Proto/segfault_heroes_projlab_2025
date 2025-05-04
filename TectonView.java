import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TectonView {
    private Tecton model;
    private transient Image image;

    public TectonView(Tecton model) throws IOException {
        this.model = model;
        image = ImageIO.read(ClassLoader.getSystemResourceAsStream("insect.png")).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    }
}
