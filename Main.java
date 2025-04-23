import java.io.IOException;

/**
 * A Main osztály felelős a program megfelelő elindulásáért.
 * Argumentum megadása nélkül normál, konzolos kezelés lehetséges.
 * 2 argumentum megadása esetén két fájl útvonalat vár a program, az elsőből olvassa a parancsokat, a másodikba írja a kimenetet.
*/
public class Main{
    public static void main(String[] args){
        try {
            IOHandler ioHandler;
            if (args.length == 2) {
                ioHandler = new IOHandler(args[0], args[1]);
            }
            else {
                ioHandler = new IOHandler("console", "console");
            }
            CommandParser commandParser = new CommandParser();
            GameManager gameManager = new GameManager(ioHandler, commandParser);
            commandParser.setGameManager(gameManager);
            boolean running = true;
            while (running) {
                String command = ioHandler.readLine();
                running = commandParser.executeCommand(command);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*Skeleton sk = new Skeleton();
        sk.JatekInditas();
        sk.FonalAgaztatas();
        sk.FonalVagas();
        sk.FonalbolFonalNovesztes();
        sk.FonalbolFonalNovesztesSporaval();
        sk.FonalonMozgas();
        sk.GombatestNovesztes();
        sk.GombatestbolFonal();
        sk.LehetsegesTektonTores();
        sk.SikertelenTektonTores();
        sk.SporaEves();
        sk.SporaSzoras();
        sk.UjKorInditasa();*/
    }
}