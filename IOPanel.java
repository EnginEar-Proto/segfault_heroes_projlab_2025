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
        ActionButtonContainer.setLayout(new BoxLayout(ActionButtonContainer, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(roundLabel);
        add(teamLabel);
        add(roleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(statsLabel);
        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel operationsLabel = createLabel("Műveletek");
        operationsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

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
            endTurnButton
        ));

        insecterActionButtons.addAll(List.of(
            cutButton,
            consumeButton,
            moveButton,
            endTurnButton
        ));

        add(ActionButtonContainer);
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
    
        ActionButtonContainer.removeAll();
        if(role.equals("Mushroomer")){
            mushroomerActionButtons.forEach(btn -> ActionButtonContainer.add(btn));
        }else if(role.equals("Insecter")){
            insecterActionButtons.forEach(btn -> ActionButtonContainer.add(btn));
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



    public void setEndTurnAction(ActionListener l) {
        IOPanel ioPanel = this;
        endTurnButton.addActionListener(l);
        endTurnButton.addActionListener(e -> {
            try {
                panel.getGuiGameManager().PlayerStep(ioPanel);
            } catch (IOException ex) {
                System.out.println("IOEXCEPTION");
            }
            panel.revalidate();
        });
    }

    public void setBranchStringButton(ActionListener l) { /*growStringButton.addMouseListener(new GrowButtonAdapter());*/ }

    public void setGrowStringAction(ActionListener l) {
        IOPanel ioPanel = this;
        growStringButton.addActionListener(l);
        growStringButton.addActionListener(e -> {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int[] selectionState = {0}; // 0 = fonál kiválasztás, 1 = start tekton, 2 = cél tekton
            final MushroomString[] selectedString = {null};
            final Tecton[] startTecton = {null};

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();

                    GUIGameManager gm = panel.getGuiGameManager();
                    Tecton clickedTecton = gm.getTectonByCoords(x, y);
                    if (clickedTecton == null) {
                        System.out.println("Nem érvényes tekton.");
                        return;
                    }

                    switch (selectionState[0]) {
                        case 0 -> {
                            // Fonál kiválasztása (ha van több is, az elsőt használjuk)
                            if (clickedTecton.getStrings().isEmpty()) {
                                System.out.println("Nincs fonál ezen a tektonon.");
                                return;
                            }
                            selectedString[0] = clickedTecton.getStrings().get(0);
                            startTecton[0] = clickedTecton;
                            selectionState[0] = 1;
                            System.out.println("Fonál kiválasztva: " + selectedString[0].getId());
                            System.out.println("Tekton kiválasztva: " + startTecton[0].getId());
                            return;
                        }
                        case 1 -> {
                            // Cél tekton kiválasztása
                            Tecton targetTecton = clickedTecton;
                            try {
                                selectedString[0].growTo(startTecton[0], targetTecton);
                                GUIGameManager.modelViewers.add(new MushroomStringView(selectedString[0]));
                                panel.getGuiGameManager().PlayerStep(ioPanel);
                                System.out.println("Fonál növesztve: " + selectedString[0].getId() + " " + startTecton[0].getId() + " " + targetTecton.getId());
                            } catch (Exception ex) {
                                System.out.println("Nem sikerült növeszteni a fonalat.");
                            }

                            panel.revalidate();
                            panel.repaint();

                            // Visszaállítás
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

    //Bálint
    public void setMoveAction(ActionListener l) {
        IOPanel ioPanel = this;
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
                            try {
                                panel.getGuiGameManager().PlayerStep(ioPanel);
                            } catch (IOException ex) {
                                System.out.println("IOEXCEPTION");
                            }
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
        IOPanel ioPanel = this;
        growMushroomBodyButton.addActionListener(l);
        growMushroomBodyButton.addActionListener(e -> {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                    ArrayList<MushroomString> strings = mushroomer.getMushroomStrings();


                    //megnézi van e csapathoz tartozó string a tektonon
                    boolean isGrowable = tec.containsString(strings);
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
                            gm.PlayerStep(ioPanel);
                            panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
    public void setEatInsectsAction(ActionListener l) {
        eatInsectsButton.addActionListener(l);
        eatInsectsButton.addActionListener(e -> {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            IOPanel ioPanel = this;

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    Tecton tec = panel.getGuiGameManager().getTectonByCoords(x, y);
                    if(tec == null) {
                        System.out.println("Nincs tecton kiválasztva");
                        return;
                    }

                    Team currentTeam = panel.getGuiGameManager().getCurrentTeam();
                    Mushroomer mushroomer = currentTeam.getMushroomer();
                    Insecter insecter = currentTeam.getInsecter();
                    ArrayList<MushroomString> strings = mushroomer.getMushroomStrings();

                    boolean isEatingOk = tec.containsString(strings);
                    if(!isEatingOk) {
                        System.out.println("Nincs fonala a csapatnak");
                        return;
                    }

                    for (Insect insect : tec.getInsects()){
                        if(insecter.getInsects().contains(insect)) continue;
                        if(insect.getAbility() != Ability.PARALYZING) continue;

                        tec.removeInsect(insect);
                        insect.getInsecter().getInsects().remove(insect);
                        GUIGameManager.modelViewers.stream().filter(view -> view.modelEquals(insect)).forEach(view -> {
                            GUIGameManager.modelViewers.remove(view);
                            System.out.println("Insect removed");
                        });
                    }

                    panel.revalidate();
                    panel.repaint();
                    try {
                        panel.getGuiGameManager().PlayerStep(ioPanel);
                    } catch (IOException ex) {

                    }
                    panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));


                }
            };

            panel.addMouseListener(mouseAdapter);

        });
    }


    //Bálint: elvileg elszórja, de nem jelenik meg
    public void setScatterSporesAction(ActionListener l) {
        IOPanel ioPanel = this;
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

                            if(selectedMushroomBody[0].scatter(targetTecton, Ability.NORMAL) == -1) {
                                System.out.println("Nincs spóra");
                                return;
                            };
                            System.out.println("Spore: " + targetTecton.getSpores().get(targetTecton.getSpores().size() - 1));
                            try {
                                GUIGameManager.modelViewers.add(new SporeView(targetTecton.getSpores()
                                .get(targetTecton.getSpores().size() - 1), 
                                gm.getCurrentTeam().getColor()));
                                panel.getGuiGameManager().PlayerStep(ioPanel);
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
                JButton insBtn = createButton(ins.getId());
                System.out.println("CutAdapter");
                insBtn.setEnabled(!ins.getAbility().equals(Ability.PARALYZING));
                insBtn.addMouseListener(new InsectSelectButtonAdapter(ins));

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

            System.out.println("InsectAdapter");

            Tecton srcTecton = insect.getTecton();
            List<Tecton> destTecton = srcTecton.getNeighbours().stream().filter(nTec ->srcTecton.canMoveTo(nTec)).toList();

            destTecton.forEach(tec -> {
                StringBuilder strB = new StringBuilder();
                JButton strRouteBtn = createButton(
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
                panel.getGuiGameManager().PlayerStep((IOPanel)ActionButtonContainer.getParent());
                ActionButtonContainer.removeAll();
                repaint();
                revalidate();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }
}

