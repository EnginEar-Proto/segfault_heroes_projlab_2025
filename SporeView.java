import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SporeView implements DrawableInterface{
    private Spore model;
    private transient Image image;
    private TeamColor color;
    private boolean eaten = false;

    public SporeView(Spore model, TeamColor c) throws IOException {
        this.model = model;
        color = c;
        switch (color) {
            case RED -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/spora1.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case BLUE -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/spora2.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case YELLOW -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/spora4.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case GREEN -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/spora3.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
            case PINK -> {
                image = ImageIO.read(ClassLoader.getSystemResourceAsStream("Assets/spora5.png")).getScaledInstance(Board.HALF_SIZE, Board.HALF_SIZE, BufferedImage.SCALE_SMOOTH);
            }
        }
    }

    public void draw(Graphics g) {
        if (eaten) return;
        if (model.getTecton() == null) return;
        //System.out.println("spora rajzolva");
        int[] pos = model.getTecton().getPosition();
        Graphics2D g2d = (Graphics2D) g;
        System.out.println(model);
        if (image != null) g2d.drawImage(image, pos[0] * Board.SQUARE_SIZE + Board.HALF_SIZE , pos[1] * Board.SQUARE_SIZE, null);
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean modelEquals(Object o) {
        return model.equals(o);
    }

    public Spore getModel() {
        return model;
    }
}
