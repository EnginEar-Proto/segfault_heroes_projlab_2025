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
    public void executeCommand(String command) throws IOException {
        if (gm == null) {
            System.out.println("GameManager nem lett beállítva.");
            return;
        }

        if (command == null || command.isEmpty()) {
            gm.getIOHandler().writeLine("Üres parancs.");
            return;
        }

        String[] parts = command.split(" ", 2);
        String action = parts[0].toLowerCase();
        String parameters = parts.length > 1 ? parts[1] : "";

        switch (action) {
            case "newgame":
                handleNewGame(parameters);
                break;
            case "pos-alloc":
                handlePosAlloc(parameters);
                break;
            case "start":
                handleStart(parameters);
                break;
            case "growstring":
                handleGrowString(parameters);
                break;
            case "move":
                handleMove(parameters);
                break;
            case "branch":
                handleBranch(parameters);
                break;
            case "scatterspores":
                handleGrowMushroomBody(parameters);
                break;
            case "eatspore":
                handleEatSpore(parameters);
                break;
            case "sum":
                handleSum(parameters);
                break;
            case "eatinsect":
                handleEatInsect(parameters);
                break;
            case "cut":
                handleCut(parameters);
                break;
            case "breaktecton":
                handleBreakTecton(parameters);
                break;
            case "time":
                handleTime(parameters);
                break;
            default:
                gm.getIOHandler().writeLine("Ismeretlen parancs: " + action);
                break;
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
