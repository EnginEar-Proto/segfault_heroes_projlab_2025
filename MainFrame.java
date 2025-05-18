import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private GamePanel gamePanel = new GamePanel(new GUIGameManager());
    private IOPanel ioPanel = new IOPanel(gamePanel);

    private static final int width = 1143;
    private static final int height = 909;

    public MainFrame(String title) {
        super(title);

        ioPanel.setGrowStringAction(e -> {
            System.out.println(">> GrowMushroomString");
        });
        ioPanel.setMoveAction(e -> {
            System.out.println(">> MoveInsect");
        });
        ioPanel.setGrowMushroomBodyAction(e -> {
            System.out.println(">> GrowMushroomBody");
        });
        ioPanel.setScatterSporesAction(e -> {
            System.out.println(">> ScatterSpores");
        });
        ioPanel.setBranchStringButton(e -> {
            System.out.println(">> BranchString");
        });
        ioPanel.setEatInsectsAction(e -> {
            System.out.println(">> StringEatsInsect");
        });
        ioPanel.setCutAction(e -> {
            System.out.println(">> CutString");
        });
        ioPanel.setEndTurnAction(e -> {
            System.out.println(">> EndTurn");
        });
        ioPanel.setConsumeAction(e -> {
            System.out.println(">> Consume");
        });


        setMinimumSize(new Dimension(width, height));
        setSize(width, height);
        setResizable(true);
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new FungoriumWindowAdapter());

        add(gamePanel);
        setVisible(true);
        gamePanel.setVisible(true);
        repaint();
    }

    /**
     * Inner class, amely a WindowAdaptertől öröklődik.
     * Bezárás esetén megkérdezi a felhasználót, hogy valóban be akarja-e zárni az ablakot.
     * Ha igen, akkor bezárja az ablakot, ha nem, akkor nem csinál semmit.
     */
    private static class FungoriumWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int result = JOptionPane.showConfirmDialog(
                    e.getWindow(),
                    "Do you really want to close the application?\nAll data will be lost!",
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

    public void startNewGame(int teamCount) throws IOException {
        TeamSelectionPanel teamSelectionPanel = new TeamSelectionPanel();
        add(teamSelectionPanel);
        for (int i = 0; i < teamCount; i++) {
            String[] names = teamSelectionPanel.requestPlayerNames(this, i + 1);
            Team team = new Team(names[0], new Insecter(names[1]), new Mushroomer(names[2]), TeamColor.values()[i]);
            gamePanel.getGuiGameManager().addTeam(team);
        }
        remove(teamSelectionPanel);
        clearFrame();

        add(gamePanel, BorderLayout.CENTER);
        add(ioPanel,  BorderLayout.EAST);
        gamePanel.setVisible(true);
        gamePanel.getGuiGameManager().initializeMap();
        gamePanel.getGuiGameManager().setStartingPosition();
        gamePanel.revalidate();
        gamePanel.repaint();

        ioPanel.setVisible(true);
        ioPanel.updateState(1, gamePanel.getGuiGameManager().getTeams()[0],
        "Mushroomer",
        0,
            gamePanel.getGuiGameManager().getTeams()[0].getMushroomer().getMushroomBodies().size());


        
        revalidate();
        repaint();
    }

    /**
     * A frame összes elemét törli, majd megjeleníti az üres frame-et.
     */
    public void clearFrame() {
        getContentPane().removeAll();
        revalidate();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        gamePanel.paintComponent(g);
    }
}
