import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InsectView implements DrawableInterface {
    private Insect model;
    private transient Image image;
    private Color color;

    public InsectView(Insect model, Color c) throws IOException {
        this.model = model;
        color = c;
        //TODO : color szerinti betöltés
        // Itt a színes betötésnél nem lenne egyszerűbb, ha hozzáfűzzük a fájl eléréshez a csaptindexet?
        // Ahhoz viszont szükséges van arra, hogy a rovarász mely csapthoz tartozása kikövetkeztethető legyen.
        image = ImageIO.read(ClassLoader.getSystemResourceAsStream("insect.png")).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
    }

    public void draw(Graphics g) {
        System.out.println("rovar rajzolva");
        int[] pos = model.getTecton().getPosition();
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) g2d.drawImage(image, pos[0] * Board.SQUARE_SIZE, pos[1] * Board.SQUARE_SIZE, null);
    }
}
