import java.io.IOException;

/**
 * A Main osztály felelős a program megfelelő elindulásáért.
 * Argumentum megadása nélkül normál, konzolos kezelés lehetséges.
 * 2 argumentum megadása esetén két fájl útvonalat vár a program, az elsőből olvassa a parancsokat, a másodikba írja a kimenetet.
*/
public class Main{
    public static void main(String[] args){
        try {
            boolean testMode;
            IOHandler ioHandler;
            if (args.length == 2) {
                ioHandler = new IOHandler(args[0], args[1]);
                testMode = true;
            }
            else {
                ioHandler = new IOHandler("console", "console");
                testMode = false;
            }

            CommandParser commandParser = new CommandParser();
            GameManager gameManager = new GameManager(ioHandler, commandParser);
            commandParser.setGameManager(gameManager);

            if (testMode) {
                gameManager.test(); // Tesztelés esetén a fájlból végigolvassa a sorokat
            }
            else {
                boolean running = true;
                boolean gameStarted;
                while (running) {
                    gameStarted = gameManager.getGameStarted();
                    if (!gameStarted) { // Normál játékmódban a játék előtti dolgokhoz
                        String command = ioHandler.readLine();
                        running = commandParser.executeCommand(command);
                    } else {
                        gameManager.play(); // Indítás után a játék saját főciklusa.
                    }
                }
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