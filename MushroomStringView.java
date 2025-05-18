import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.IOException;

public class MushroomStringView implements DrawableInterface{
    private MushroomString model;
    private transient Image image;

    public MushroomStringView(MushroomString model) throws IOException {
        this.model = model;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<Tecton> tectons = model.getTectons();
        g2d.setColor(Color.WHITE);
        System.out.print("mushroomstring rajzolva: ");
        for (int i = 0; i < tectons.size() - 1; i++) {
            g2d.drawLine(tectons.get(i).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i + 1).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i + 1).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE);
            System.out.print(tectons.get(i) + " ");
        }
        System.out.println();
    }
}
