import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.IOException;

public class MushroomStringView implements DrawableInterface{
    private MushroomString model;
    private transient Image image;
    private double number1;
    private double number2;

    public MushroomStringView(MushroomString model) throws IOException {
        this.model = model;
        number1 = Math.random();
        number2 = Math.random();
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
        // A második vonal végpontjának kiszámítása
        int lastX = tectons.get(tectons.size() - 1).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE;
        int lastY = tectons.get(tectons.size() - 1).getPosition()[1] * Board.SQUARE_SIZE + Board.HALF_SIZE;

        int last2_X = tectons.get(tectons.size() - 2).getPosition()[0] * Board.SQUARE_SIZE + Board.HALF_SIZE;
        int last2_Y = tectons.get(tectons.size() - 2).getPosition()[1] * Board.SQUARE_SIZE + Board.HALF_SIZE;

        // Eredeti végpont plusz a random eltolás, figyelve a pálya határain
        int newX;
        int newY;
        if(last2_X<lastX){
            newX = lastX + (int)(number1 * Board.HALF_SIZE);
        }else{
            newX = lastX - (int)(number2 * Board.HALF_SIZE);
        }
        if(last2_Y<lastY){
            newY = lastY + (int)(number1 * Board.HALF_SIZE);
        }else {
            newY = lastY - (int)(number2 * Board.HALF_SIZE);
        }
        // A vonal végpontjának biztosítása, hogy a négyzetek területén belül maradjon
        newX = Math.max((int)((double)Board.HALF_SIZE*0.6), Math.min(Board.SQUARE_SIZE * (Board.SQUARE_SIZE - 1) + Board.HALF_SIZE, newX));
        newY = Math.max((int)((double)Board.HALF_SIZE*0.6), Math.min(Board.SQUARE_SIZE * (Board.SQUARE_SIZE - 1) + Board.HALF_SIZE, newY));

        // A kiegészítő vonal kirajzolása
        g2d.drawLine(lastX, lastY, newX, newY);
        //System.out.println();
    }
    public boolean modelEquals(Object o) {
        return model.equals(o);
    }
}
