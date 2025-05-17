import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private GamePanel gamePanel = new GamePanel(new GUIGameManager());
    private IOPanel ioPanel = new IOPanel(gamePanel);

    private static final int width = 1143;
    private static final int height = 909;

    public MainFrame(String title) {
        super(title);

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
        ioPanel.updateState(1, gamePanel.getGuiGameManager().getTeams()[0].getName(), 
        "Mushroomer",
        0,
            gamePanel.getGuiGameManager().getTeams()[0].getMushroomer().getMushroomBodies().size());

        ioPanel.setGrowMushroomBodyAction(new ActionListener() {
            final int[] click = new int[2];

            @Override
            public void actionPerformed(ActionEvent e) {

                //az a listener, ami a játéktéren figyeli a kattintásokat
                MouseListener listener = new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        click[0] = e.getX();
                        click[1] = e.getY();

                        //választott tecton megkeresése
                        Tecton tec = gamePanel.getGuiGameManager().getTectonByCoords(e.getX(), e.getY());
                        GUIGameManager gm = gamePanel.getGuiGameManager();

                        if(tec == null) {
                            //TODO: kéne vmi visszajelzés
                            System.out.println("Nincs tecton");
                            return;
                        }

                        //ha van már gombatest a tektonon, nem lehet folytatni a műveletet
                        if(tec.getMushroomBody() != null) {
                            System.out.println("Már van gomba");
                            return;
                        }

                        //az adott csapat stringjeinek összegyűjtése (lehet kéne fgv)
                        Mushroomer mushroomer = gm.getCurrentTeam().getMushroomer();
                        ArrayList<MushroomString> strings = new ArrayList<>();
                        for (int i = 0; i < mushroomer.getMushroomBodies().size(); i++){
                            MushroomBody mb = mushroomer.getMushroomBodies().get(i);
                            strings.addAll(mb.getStrings());
                        }


                        //megnézi van e csapathoz tartozó string a tektonon
                        boolean isGrowable = false;
                        for (int i = 0; i < tec.getStrings().size(); i++) {
                            if(strings.contains(tec.getStrings().get(i))) {
                                isGrowable = true;
                                break;
                            }
                        }
                        //TODO: visszajelzés
                        if(!isGrowable) {
                            System.out.println("Nincs string");
                            return;
                        }

                        //hozzáadja a játékohoz az új gombát
                        if(tec.growBody()){
                            try{
                                mushroomer.addMushroomBody(tec.getMushroomBody());
                                GUIGameManager.modelViewers.add(new MushroomBodyView(tec.getMushroomBody(), gm.getCurrentTeam().getColor()));
                                gamePanel.removeMouseListener(this);
                                gamePanel.revalidate();
                                gamePanel.repaint();
                                System.out.println("BODYADDED");
                            } catch (IOException exception) {
                                System.out.println("IOEXCEPTION");
                            }
                        } else {
                            System.out.println("Vmi gatya");
                            return;
                        }


                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                };

                gamePanel.addMouseListener(listener);

                //gamePanel.removeMouseListener();
            }
        });


        
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
