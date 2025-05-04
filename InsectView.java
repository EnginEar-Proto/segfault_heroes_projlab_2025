import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InsectView implements DrawableInterface {
    private Insect model;
    private transient Image image;

    public InsectView(Insect model) throws IOException {
        this.model = model;
        image = ImageIO.read(ClassLoader.getSystemResourceAsStream("insect.png")).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        System.out.println("rovar rajzolva");
        int[] pos = model.getTecton().getPosition();
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) g2d.drawImage(image, pos[0], pos[1], null);
    }
}
