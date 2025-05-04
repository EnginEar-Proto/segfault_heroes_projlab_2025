import java.awt.*;
import java.awt.Color;

public class Board implements DrawableInterface {
    /**
     * Sorok száma.
     */
    final int row = 15;
    /**
     * Oszlopok száma.
     */
    final int col = 15;
    /**
     * Mezők mérete pixelben.
     */
    public static final int SQUARE_SIZE = 50;

    /**
     * Mezők méretének fele, más pozíciók számításához hasznos.
     */
    public static final int HALF_SIZE = SQUARE_SIZE / 2;

    /**
     * A rajzolásért felelős metódus.
     * Felváltva rajzolja a sötét és világos mezőket.
     * @param g ez az objektum rajzolja a táblát
     */
    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(154, 154, 154));
        graphics2D.fillRect(0, 0, SQUARE_SIZE * 15, SQUARE_SIZE * 15);
    }
}
