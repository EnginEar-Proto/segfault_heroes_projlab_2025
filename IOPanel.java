import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOPanel extends JPanel {
    private JLabel roundLabel;
    private JLabel teamLabel;
    private JLabel roleLabel;
    private JLabel statsLabel;

    private JButton cutButton;
    private JButton consumeButton;
    private JButton moveButton;
    private JButton endTurnButton;

    private JButton growStringButton;
    private JButton branchStringButton;
    private JButton growMushroomBodyButton;
    private JButton eatInsectsButton;
    private JButton scatterSporesButton;

    private GamePanel panel;

    //TODO: Hozzáadni az osztálydiagrammhoz
    private ArrayList<JButton> mushroomerActionButtons, insecterActionButtons;
    private JPanel ActionButtonContainer;

    public IOPanel(GamePanel p) {
        panel = p;
        mushroomerActionButtons = new ArrayList<>();
        insecterActionButtons = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(67, 87, 112));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        roundLabel = createLabel("Kör: ");
        teamLabel = createLabel("Csapat: ");
        roleLabel = createLabel("Szerep: ");
        statsLabel = createLabel("<html>Csapat statisztikák<br>Tápanyag pontok: 0<br>Gombatestek: 0</html>");
        ActionButtonContainer = new JPanel(); 

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(roundLabel);
        add(teamLabel);
        add(roleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(statsLabel);
        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel operationsLabel = createLabel("Műveletek");
        operationsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(operationsLabel);

        cutButton = createButton("Fonál Vágás");
        cutButton.setIcon(new ImageIcon("cut.png"));
        consumeButton = createButton("Spóra fogyasztás");
        consumeButton.setIcon(new ImageIcon("spore1.png"));

        moveButton = createButton("Rovar mozgatása");
        endTurnButton = createButton("Lépés tovább adása");

        growStringButton = createButton("Fonál növesztés");
        growStringButton.setIcon(new ImageIcon("growstring.png"))
        ;
        branchStringButton = createButton("Fonál ágaztatás");
        branchStringButton.setIcon(new ImageIcon("branch.png"));

        growMushroomBodyButton = createButton("Gombatest növesztés");
        growMushroomBodyButton.setIcon(new ImageIcon("growbody.png"));

        eatInsectsButton = createButton("Rovar evés");
        scatterSporesButton = createButton("Spóra szórás");


        mushroomerActionButtons.addAll(List.of(
            growStringButton,
            branchStringButton,
            growMushroomBodyButton,
            eatInsectsButton,
            scatterSporesButton,
            cutButton,
            endTurnButton
        ));

        insecterActionButtons.addAll(List.of(
            cutButton,
            consumeButton,
            moveButton,
            endTurnButton
        ));

        add(growStringButton);
        add(branchStringButton);
        add(growMushroomBodyButton);
        add(eatInsectsButton);
        add(scatterSporesButton);

        add(cutButton);
        add(consumeButton);
        add(moveButton);
        add(endTurnButton);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public void updateState(int round, Team team, String role, int nutrients, int bodies) {
        roundLabel.setText("Kör: " + round);
        teamLabel.setText("Csapat: " + team.getName());
        roleLabel.setText("Szerep: " + role);
        statsLabel.setText("<html>Csapat statisztikák<br>Tápanyag pontok: " + nutrients + "<br>Gombatestek: " + bodies + "</html>");
    
        if(role.equals("Mushroomer")){
            mushroomerActionButtons.forEach(btn -> btn.setVisible(true));
            insecterActionButtons.forEach(btn -> btn.setVisible(false));
        }else if(role.equals("Insecter")){
            insecterActionButtons.forEach(btn -> btn.setVisible(true));
            mushroomerActionButtons.forEach(btn -> btn.setVisible(false));
        }else{
            ActionButtonContainer.removeAll();
        }

        repaint();
        revalidate();
    }

    public void showResults() {
        removeAll();
        revalidate();
        repaint();
        //TODO result kiírása szépen.
    }

    public void setCutAction(ActionListener l) { cutButton.addMouseListener(new CutButtonAdapter()); }
    public void setConsumeAction(ActionListener l) { consumeButton.addActionListener(l); }
    /*public void setMoveAction(ActionListener l) {
        moveButton.addActionListener(l);
        Insect ins = null;
        Tecton tec = null;
        panel.addMouseListener(new MouseAdapter() {

        });


    }*/



    public void setEndTurnAction(ActionListener l) { endTurnButton.addActionListener(l); }

    public void setBranchStringButton(ActionListener l) { growStringButton.addMouseListener(new GrowButtonAdapter()); }

    //Bálint
    public void setMoveAction(ActionListener l) {
        moveButton.addActionListener(l);
        moveButton.addActionListener(e -> {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Állapot követése: 0 = nincs kiválasztva semmi, 1 = rovar kiválasztva, várunk texton célpontra
            final int[] selectionState = {0};
            final Insect[] selectedInsect = {null};
            System.out.println("Kiválasztva");

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Meghatározzuk a kattintás koordinátáit
                    int x = e.getX();
                    int y = e.getY();

                    if (selectionState[0] == 0) {
                        // Első kattintás: rovar kiválasztása
                        Tecton insectTecton = panel.getGuiGameManager().getTectonByCoords(x, y);
                        System.out.println(insectTecton.getId());
                        if (insectTecton != null) {
                            selectedInsect[0] = insectTecton.getInsects().get(0);
                            selectionState[0] = 1;
                        }
                    } else if (selectionState[0] == 1) {
                        // Második kattintás: texton célpont kiválasztása
                        Tecton targetTecton = panel.getGuiGameManager().getTectonByCoords(x, y);
                        System.out.println(targetTecton.getId());
                        if (targetTecton != null) {
                            // Rovart mozgatjuk a kiválasztott tektonra
                            selectedInsect[0].moveTo(targetTecton);
                            panel.revalidate();
                            panel.repaint();

                            // Visszaállítjuk az állapotot és eltávolítjuk a figyelőt
                            selectionState[0] = 0;
                            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            panel.removeMouseListener(this);
                        }
                    }
                }
            };

            panel.addMouseListener(mouseAdapter);
        });
    }

    //Kopi
    public void setGrowMushroomBodyAction(ActionListener l) {
        growMushroomBodyButton.addActionListener(l);
        growMushroomBodyButton.addActionListener(e -> {
            final int[] click = new int[2];

            //az a listener, ami a játéktéren figyeli a kattintásokat
            MouseAdapter listener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    click[0] = e.getX();
                    click[1] = e.getY();

                    //választott tecton megkeresése
                    Tecton tec = panel.getGuiGameManager().getTectonByCoords(e.getX(), e.getY());
                    GUIGameManager gm = panel.getGuiGameManager();

                    if (tec == null) {
                        //TODO: kéne vmi visszajelzés
                        System.out.println("Nincs tecton");
                        return;
                    }

                    //ha van már gombatest a tektonon, nem lehet folytatni a műveletet
                    if (tec.getMushroomBody() != null) {
                        System.out.println("Már van gomba");
                        return;
                    }

                    //az adott csapat stringjeinek összegyűjtése (lehet kéne fgv)
                    Mushroomer mushroomer = gm.getCurrentTeam().getMushroomer();
                    ArrayList<MushroomString> strings = new ArrayList<>();
                    for (int i = 0; i < mushroomer.getMushroomBodies().size(); i++) {
                        MushroomBody mb = mushroomer.getMushroomBodies().get(i);
                        strings.addAll(mb.getStrings());
                    }


                    //megnézi van e csapathoz tartozó string a tektonon
                    boolean isGrowable = false;
                    for (int i = 0; i < tec.getStrings().size(); i++) {
                        if (strings.contains(tec.getStrings().get(i))) {
                            isGrowable = true;
                            break;
                        }
                    }
                    //TODO: visszajelzés
                    if (!isGrowable) {
                        System.out.println("Nincs string");
                        return;
                    }

                    //hozzáadja a játékohoz az új gombát
                    if (tec.growBody()) {
                        try {
                            mushroomer.addMushroomBody(tec.getMushroomBody());
                            GUIGameManager.modelViewers.add(new MushroomBodyView(tec.getMushroomBody(), gm.getCurrentTeam().getColor()));
                            panel.removeMouseListener(this);
                            panel.revalidate();
                            panel.repaint();
                            System.out.println("BODYADDED");

                        } catch (IOException exception) {
                            System.out.println("IOEXCEPTION");
                        }
                    } else {
                        System.out.println("Vmi gatya");
                        return;
                    }
                }
            };

            panel.addMouseListener(listener);


        });
    }
    //Kopi
    public void setEatInsectsAction(ActionListener l) { eatInsectsButton.addActionListener(l); }


    //Bálint: elvileg elszórja, de nem jelenik meg
    public void setScatterSporesAction(ActionListener l) {
        scatterSporesButton.addActionListener(l);
        scatterSporesButton.addActionListener(e -> {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Állapot követése: 0 = nincs kiválasztva semmi, 1 = rovar kiválasztva, várunk texton célpontra
            final int[] selectionState = {0};
            final MushroomBody[] selectedMushroomBody = {null};
            System.out.println("Kiválasztva");

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Meghatározzuk a kattintás koordinátáit
                    int x = e.getX();
                    int y = e.getY();

                    GUIGameManager gm = panel.getGuiGameManager();
                    if (selectionState[0] == 0) {
                        // Első kattintás: rovar kiválasztása
                        Tecton mushroomBodyTecton = panel.getGuiGameManager().getTectonByCoords(x, y);
                        System.out.println(mushroomBodyTecton.getId());
                        if (mushroomBodyTecton != null) {
                            selectedMushroomBody[0] = mushroomBodyTecton.getMushroomBody();
                            selectionState[0] = 1;
                        }
                    } else if (selectionState[0] == 1) {
                        // Második kattintás: texton célpont kiválasztása
                        Tecton targetTecton = panel.getGuiGameManager().getTectonByCoords(x, y);
                        System.out.println(targetTecton.getId());
                        if (targetTecton != null) {

                            selectedMushroomBody[0].scatter(targetTecton, Ability.NORMAL);
                            System.out.println("Spore: " + targetTecton.getSpores().getLast());
                            try {
                                GUIGameManager.modelViewers.add(new SporeView(targetTecton.getSpores().getLast(), gm.getCurrentTeam().getColor()));
                                System.out.println("modelAdded");

                            } catch (Exception exception) {
                                System.out.println("Vmi gatya");
                            }
                            panel.revalidate();
                            panel.repaint();

                            // Visszaállítjuk az állapotot és eltávolítjuk a figyelőt
                            selectionState[0] = 0;
                            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            panel.removeMouseListener(this);
                        } else System.out.print("gatya");
                    }
                }
            };

            panel.addMouseListener(mouseAdapter);
        });
    }

    private class CutButtonAdapter extends MouseAdapter{

        @Override
        public void mouseClicked(MouseEvent e) {
            //Előző gombok eltüntetése
            ActionButtonContainer.removeAll();

            //Rovarok kilistázása (amely rovar lebénult, az disabled button-ként jelenjen meg az enumerációban)
            panel.getGuiGameManager()
            .getCurrentTeam()
            .getInsecter().getInsects()
            .forEach(ins -> {
                JButton insBtn = new JButton(ins.getId());
                insBtn.setEnabled(!ins.getAbility().equals(Ability.PARALYZING));
                insBtn.addMouseListener(new InsectSelectButtonAdapter(ins));

                insBtn.setVisible(true);
                ActionButtonContainer.add(insBtn);
                repaint();
                revalidate();
            });
        }
    }

    private class InsectSelectButtonAdapter extends MouseAdapter{
        private Insect insect;

        public InsectSelectButtonAdapter(Insect _insect){
            super();
            insect = _insect;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            ActionButtonContainer.removeAll();

            Tecton srcTecton = insect.getTecton();
            List<Tecton> destTecton = srcTecton.getNeighbours().stream().filter(nTec ->srcTecton.canMoveTo(nTec)).toList();

            destTecton.forEach(tec -> {
                StringBuilder strB = new StringBuilder();
                JButton strRouteBtn = new JButton(
                    strB.append(srcTecton.getId())
                    .append(" -> ")
                    .append(tec.getId()).toString()
                );

                MushroomString str = srcTecton.getStrings()
                .stream().filter(s -> s.getTectons().contains(tec))
                .findFirst()
                .get();

                strRouteBtn.addMouseListener(new StringSelectButtonAdapter(str, insect, tec));

                strRouteBtn.setVisible(true);
                ActionButtonContainer.add(strRouteBtn);
                repaint();
                revalidate();
            });
        }
    }

    private class StringSelectButtonAdapter extends MouseAdapter {
        private MushroomString str;
        private Insect insect;
        private Tecton dest;

        public StringSelectButtonAdapter(MushroomString _str, Insect _insect, Tecton _dest){
            super();
            str = _str;
            insect = _insect;
            dest = _dest;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            try {
                insect.sabotageString(str, insect.getTecton(), dest);
                ActionButtonContainer.removeAll();
                repaint();
                revalidate();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    private class GrowButtonAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e){
            // Felajánlja az összes olyan tektont amelyről nőhet fonal.
            repaint();
            revalidate();
        }
    }

    private class SourceTectonButtonAdapter extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e){
            // Felajánlja az összes olyan tektont amelyre nőhet fonal.
            repaint();
            revalidate();
        }
    }

    private class DestTectonButtonAdapter extends MouseAdapter {
        private Tecton sourceTecton;
        
        @Override
        public void mouseClicked(MouseEvent e){
            // Feldolgozza a fonal nővesztést.
            repaint();
            revalidate();
        }
    }
}

