import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TectonView implements DrawableInterface{
    private Tecton model;
    private transient Image image;

    public TectonView(Tecton model) throws IOException {
        this.model = model;
        switch (model.getSize()){
            case 3:
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/Tecton.png")).getScaledInstance((Board.SQUARE_SIZE * 2), (Board.SQUARE_SIZE * 2), BufferedImage.SCALE_SMOOTH);
                break;
            case 2:
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/Tecton3.png")).getScaledInstance((Board.SQUARE_SIZE * 2), Board.SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
                break;
            case 1:
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/Tecton2.png")).getScaledInstance(Board.SQUARE_SIZE, Board.SQUARE_SIZE, BufferedImage.SCALE_SMOOTH);
                break;
        }
    }

    public void draw(Graphics g) {
        System.out.println("tekton rajzolva, meret: " + model.getSize());
        int[] pos = model.getPosition();
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) g2d.drawImage(image, pos[0] * Board.SQUARE_SIZE, pos[1] * Board.SQUARE_SIZE, null);
    }

    public Tecton getModel() {
        return model;
    }
}
