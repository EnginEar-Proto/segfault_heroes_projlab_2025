import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.IOException;

public class MushroomStringView implements DrawableInterface{
    private MushroomString model;
    private transient Image image;
    private double number;

    public MushroomStringView(MushroomString model) throws IOException {
        this.model = model;
        number = -1 + Math.random() * 2;
    }

    public MushroomString getModel(){
        return model;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        List<Tecton> tectons = model.getTectons();
        g2d.setColor(Color.WHITE);
        //System.out.print("mushroomstring rajzolva: ");
        for (int i = 0; i < tectons.size() - 1; i++) {
            g2d.drawLine(tectons.get(i).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i).getPosition()[1] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i + 1).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(i + 1).getPosition()[1] * Board.SQUARE_SIZE + Board.HALF_SIZE);
            //System.out.print(tectons.get(i).getId() + " " + tectons.get(i + 1).getId() + " ");
        }
        g2d.drawLine(tectons.get(tectons.size() - 1).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(tectons.size() - 1).getPosition()[1] * Board.SQUARE_SIZE + Board.HALF_SIZE, tectons.get(tectons.size() - 1).getPosition()[0] * Board.SQUARE_SIZE + (int)(Math.sin(number) * (double)Board.HALF_SIZE), tectons.get(tectons.size() - 1).getPosition()[1] * Board.SQUARE_SIZE + (int)(Math.cos(number) * (double)Board.HALF_SIZE));

        //System.out.println();
    }
    public boolean modelEquals(Object o) {
        return model.equals(o);
    }
}
