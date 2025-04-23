import java.io.IOException;

/**
 * A CommandParser osztály felelős a string alapú parancsok értelmezéséért és
 * a megfelelő kezelőmetódusok meghívásáért.
 */
public class CommandParser {

    private GameManager gm;

    /**
     * Beállítja a GameManager példányt, amit később a parancsok feldolgozásánál használ.
     *
     * @param gm A játékot kezelő GameManager példány.
     */
    public void setGameManager(GameManager gm) {
        this.gm = gm;
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

        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();
        String parameters = parts.length > 1 ? parts[1] : "";

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

    public void handleNewGame(String parameters) {
        // Implementáció később
    }

    public void handlePosAlloc(String parameters) {
        // Implementáció később
    }

    public void handleStart(String parameters) {
        // Implementáció később
    }

    public void handleGrowString(String parameters) {
        // Implementáció később
    }

    public void handleMove(String parameters) {
        // Implementáció később
    }

    public void handleBranch(String parameters) {
        // Implementáció később
    }

    public void handleGrowMushroomBody(String parameters) {
        // Implementáció később
    }

    public void handleEatSpore(String parameters) {
        // Implementáció később
    }

    public void handleSum(String parameters) {
        // Implementáció később
    }

    public void handleEatInsect(String parameters) {
        // Implementáció később
    }

    public void handleCut(String parameters) {
        // Implementáció később
    }

    public void handleBreakTecton(String parameters) {
        // Implementáció később
    }

    public void handleTime(String parameters) {
        // Implementáció később
    }
}
