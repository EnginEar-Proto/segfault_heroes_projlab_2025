import java.io.IOException;

/**
 * A Main osztály felelős a program megfelelő elindulásáért.
*/
public class Main{
    public static void main(String[] args){
        try {
            IOHandler ioHandler = new IOHandler("console", "console");
            CommandParser commandParser = new CommandParser();
            GameManager gameManager = new GameManager(ioHandler, commandParser);
            commandParser.setGameManager(gameManager);
            boolean running = true;
            while (running){
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