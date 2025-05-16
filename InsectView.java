import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InsectView implements DrawableInterface {
    private Insect model;
    private transient Image image;
    private TeamColor color;

    public InsectView(Insect model, TeamColor c) throws IOException {
        this.model = model;
        color = c;
        switch (color) {
            case RED -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/rovar1.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case BLUE -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/rovar2.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case YELLOW -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/rovar4.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case GREEN -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/rovar3.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case PINK -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/rovar5.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
        }
    }

    public void draw(Graphics g) {
        System.out.println("rovar rajzolva");
        int[] pos = model.getTecton().getPosition();
        Graphics2D g2d = (Graphics2D) g;
        if (image != null) g2d.drawImage(image, pos[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, pos[1] * Board.SQUARE_SIZE + Board.HALF_SIZE, null);
    }
}
