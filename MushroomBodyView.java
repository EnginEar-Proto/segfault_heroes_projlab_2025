import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MushroomBodyView implements DrawableInterface{
    private MushroomBody model;
    private transient Image image;
    private TeamColor color;

    public MushroomBodyView(MushroomBody model, TeamColor c) throws IOException {
        this.model = model;
        color = c;
        switch (color) {
            case RED -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/gomba1.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case BLUE -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/gomba2.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case YELLOW -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/gomba4.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case GREEN -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/gomba3.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case PINK -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/gomba5.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
        }
    }

    public void draw(Graphics g) {
        System.out.println("gombatest rajzolva");
        int[] pos = model.getTecton().getPosition();
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) g2d.drawImage(image, pos[0] * Board.SQUARE_SIZE, pos[1] * Board.SQUARE_SIZE, null);
    }
}
