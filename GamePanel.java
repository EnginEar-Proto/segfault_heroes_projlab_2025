import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GUIGameManager guiGameManager;

    public GamePanel(GUIGameManager guiGameManager) {
        this.guiGameManager = guiGameManager;
        guiGameManager.setGamePanel(this);
    }

    public GUIGameManager getGuiGameManager() {
        return guiGameManager;
    }

    @Override
    protected void paintComponent(Graphics g) {
        guiGameManager.render(g);
    }
}
