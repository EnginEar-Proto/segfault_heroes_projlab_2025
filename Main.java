import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A Main osztály felelős a program megfelelő elindulásáért.
 * Argumentum megadása nélkül normál, konzolos kezelés lehetséges.
 * 2 argumentum megadása esetén két fájl útvonalat vár a program, az elsőből olvassa a parancsokat, a másodikba írja a kimenetet.
 * 2 argumentum megadása esetén, ha "GUI" paraméter van megadva, akkor elindul a grafikus interfésszel a játék, a második paraméterként megadott csapatszámmal, ami 2-től 5 lehet.
*/
public class Main{
    public static void main(String[] args) {
        if (args[0].equals("GUI") && args.length == 2) {
            if (Integer.parseInt(args[1]) < 2 || Integer.parseInt(args[1]) > 5){
                System.out.println("Rossz csapat szám.");
            }
            else {
                MainFrame window = new MainFrame("Fungorium");
                window.setVisible(true);
                window.startNewGame(Integer.parseInt(args[1]));
            }
        } else {
            try {
                boolean testMode;
                IOHandler ioHandler;
                if (args.length == 2) {
                    ioHandler = new IOHandler(args[0], args[1]);
                    testMode = true;
                } else {
                    ioHandler = new IOHandler("console", "console");
                    testMode = false;
                }

                CommandParser commandParser = new CommandParser();
                GameManager gameManager = new GameManager(ioHandler, commandParser);
                commandParser.setGameManager(gameManager);

                if (testMode) {
                    gameManager.test(); // Tesztelés esetén a fájlból végigolvassa a sorokat
                } else {
                    boolean running = true;
                    boolean gameStarted;
                    while (running) {
                        gameStarted = gameManager.getGameStarted();
                        if (!gameStarted) { // Normál játékmódban a játék előtti dolgokhoz
                            String command = ioHandler.readLine();
                            running = commandParser.executeCommand(command);
                            while (!running) {
                                command = ioHandler.readLine();
                                running = commandParser.executeCommand(command);
                            }
                        } else {
                            gameManager.play(); // Indítás után a játék saját főciklusa.
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}