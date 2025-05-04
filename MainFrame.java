import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private GamePanel gamePanel;

    private static final int size = 800;

    public MainFrame(String title) {
        super(title);

        setMinimumSize(new Dimension(size, size));
        setSize(size, size);
        setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CheckersWindowAdapter());

    }

    /**
     * Inner class, amely a WindowAdaptertől öröklődik.
     * Bezárás esetén megkérdezi a felhasználót, hogy valóban be akarja-e zárni az ablakot.
     * Ha igen, akkor bezárja az ablakot, ha nem, akkor nem csinál semmit.
     */
    private static class CheckersWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int result = JOptionPane.showConfirmDialog(
                    e.getWindow(),
                    "Do you really want to close the application?\nAll unsaved data will be lost!",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Window is closing...");
                e.getWindow().dispose();
            } else {
                System.out.println("Window close canceled.");
            }
        }
    }

    public void startNewGame(int teamCount) {
        TeamSelectionPanel teamSelectionPanel = new TeamSelectionPanel();
        add(teamSelectionPanel);
        for (int i = 0; i < teamCount; i++) {
            teamSelectionPanel.requestPlayerNames(this);
        }
        remove(teamSelectionPanel);
    }

    /**
     * A frame összes elemét törli, majd megjeleníti az üres frame-et.
     */
    public void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }
}
