import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A CommandParser osztály felelős a string alapú parancsok értelmezéséért és
 * a megfelelő kezelőmetódusok meghívásáért.
 */
public class CommandParser {

    private GameManager gm;
    private IOHandler ioHandler;

    /**
     * Beállítja a GameManager példányt, amit később a parancsok feldolgozásánál használ.
     *
     * @param gm A játékot kezelő GameManager példány.
     */
    public void setGameManager(GameManager gm) {
        this.gm = gm;
        this.ioHandler = gm.getIOHandler();
    }

    /**
     * Feldolgozza a kapott parancsot, és meghívja a megfelelő handler függvényt.
     *
     * @param command A beolvasott parancs szövege.
     */
    public boolean executeCommand(String command) throws IOException {
        if (gm == null) {
            System.out.println("GameManager nem lett beállítva.");
            return false;
        }

        if (command == null || command.isEmpty()) {
            gm.getIOHandler().writeLine("Üres parancs.");
            return false;
        }

        String[] parts = command.split(" ");
        String action = parts[0].toLowerCase();
        List<String> paramsAsList = new ArrayList<>(Arrays.asList(parts));
        paramsAsList.removeFirst();
        String[] parameters = paramsAsList.toArray(new String[paramsAsList.size()]);

        switch (action) {
            case "newgame":
                handleNewGame(parameters);
                return true;
            case "pos-alloc":
                handlePosAlloc(parameters);
                return true;
            case "start":
                handleStart(parameters);
                return true;
            case "growstring":
                handleGrowString(parameters);
                return true;
            case "move":
                handleMove(parameters);
                return true;
            case "branch":
                handleBranch(parameters);
                return true;
            case "scatterspores":
                handleGrowMushroomBody(parameters);
                return true;
            case "eatspore":
                handleEatSpore(parameters);
                return true;
            case "sum":
                handleSum(parameters);
                return true;
            case "eatinsect":
                handleEatInsect(parameters);
                return true;
            case "cut":
                handleCut(parameters);
                return true;
            case "breaktecton":
                handleBreakTecton(parameters);
                return true;
            case "time":
                handleTime(parameters);
                return true;
            case "exit":
                return false;
            default:
                gm.getIOHandler().writeLine("Ismeretlen parancs: " + action);
                return true;
        }
    }

    /**
     * Kezeli a 'newgame' parancsot, amely új csapat(ok) létrehozását és hozzáadását végzi a játékhoz.
     *
     * Ha nem adnak meg paramétert, akkor interaktív módon olvas be csapatokat addig,
     * amíg a felhasználó be nem írja az "xxx" szót csapatnévként. Minden csapathoz
     * be kell kérni a gombász és a rovarász nevét is.
     *
     * Ha a "-t" paraméterrel hívják meg, akkor a paraméter utáni szám meghatározza, hány csapatot kell létrehozni.
     * Ebben az esetben az adott számú csapat kerül bekérésre.
     *
     * Hibás paraméter esetén hibaüzenetet ír ki.
     *
     * @param parameters A parancs argumentumai, pl. {"-t", "3"} több csapat létrehozásához.
     * @throws IOException Ha a bemenet/kiírás során hiba történik.
     */
    public void handleNewGame(String[] parameters) throws IOException {
        gm.resetTeams();

        String teamName;
        String player1;
        String player2;

        if (parameters.length == 0) {
            while (true) {
                ioHandler.write("Csapatnév: ");
                teamName = ioHandler.readLine();
                if (teamName.equalsIgnoreCase("xxx"))
                    return;
                ioHandler.write("Gombász: ");
                player1 = ioHandler.readLine();
                ioHandler.write("Rovarász: ");
                player2 = ioHandler.readLine();

                Team team = new Team(teamName, new Insecter(player2), new Mushroomer(player1));
                gm.addTeam(team);
            }
        }
        else if (parameters[0].equals("-t") && parameters.length == 2) {
            int count = Integer.parseInt(parameters[1]);
            while (count-- > 0) {
                ioHandler.write("Csapatnév: ");
                teamName = ioHandler.readLine();
                ioHandler.write("Gombász: ");
                player1 = ioHandler.readLine();
                ioHandler.write("Rovarász: ");
                player2 = ioHandler.readLine();

                Team team = new Team(teamName, new Insecter(player2), new Mushroomer(player1));
                gm.addTeam(team);
            }
        }
        else {
            ioHandler.writeLine("HIBA: ismeretlen newgame paraméter");
        }
    }

    public void handlePosAlloc(String[] parameters) {
        // Implementáció később
    }

    public void handleStart(String[] parameters) {
        gm.setGameStarted(true);
        gm.setLaps(1);
    }

    public void handleGrowString(String[] parameters) {
        // Implementáció később
    }

    public void handleMove(String[] parameters) {
        // Implementáció később
    }

    public void handleBranch(String[] parameters) {
        // Implementáció később
    }

    public void handleGrowMushroomBody(String[] parameters) {
        // Implementáció később
    }

    public void handleEatSpore(String[] parameters) {
        // Implementáció később
    }

    public void handleSum(String[] parameters) {
        // Implementáció később
    }

    public void handleEatInsect(String[] parameters) {
        // Implementáció később
    }

    public void handleCut(String[] parameters) {
        // Implementáció később
    }

    public void handleBreakTecton(String[] parameters) {
        // Implementáció később
    }

    public void handleTime(String[] parameters) {
        // Implementáció később
    }
}
