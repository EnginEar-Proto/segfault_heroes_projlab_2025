import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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

    public void updateState(int round, String team, String role, int nutrients, int bodies) {
        roundLabel.setText("Kör: " + round);
        teamLabel.setText("Csapat: " + team);
        roleLabel.setText("Szerep: " + role);
        statsLabel.setText("<html>Csapat statisztikák<br>Tápanyag pontok: " + nutrients + "<br>Gombatestek: " + bodies + "</html>");
    
        if(role.equals("Mushroomer")){
            mushroomerActionButtons.forEach(btn -> btn.setVisible(true));
            insecterActionButtons.forEach(btn -> btn.setVisible(false));
        }else if(role.equals("Insecter")){
            insecterActionButtons.forEach(btn -> btn.setVisible(true));
            mushroomerActionButtons.forEach(btn -> btn.setVisible(false));
        }else{
            insecterActionButtons.forEach(btn -> btn.setVisible(false));
            mushroomerActionButtons.forEach(btn -> btn.setVisible(false));
        }

        repaint();
        revalidate();
    }


    public void setCutAction(ActionListener l) { cutButton.addActionListener(l); }
    public void setConsumeAction(ActionListener l) { consumeButton.addActionListener(l); }
    /*public void setMoveAction(ActionListener l) {
        moveButton.addActionListener(l);
        Insect ins = null;
        Tecton tec = null;
        panel.addMouseListener(new MouseAdapter() {

        });


    }*/



    public void setEndTurnAction(ActionListener l) { endTurnButton.addActionListener(l); }

    public void setGrowStringAction(ActionListener l) { growStringButton.addActionListener(l); }
    public void setBranchStringAction(ActionListener l) { branchStringButton.addActionListener(l); }

    //Kopi
    public void setGrowMushroomBodyAction(ActionListener l) { growMushroomBodyButton.addActionListener(l); }
    //Kopi
    public void setEatInsectsAction(ActionListener l) { eatInsectsButton.addActionListener(l); }
    //Kopi
    public void setscatterSporesAction(ActionListener l) { scatterSporesButton.addActionListener(l); }
}

