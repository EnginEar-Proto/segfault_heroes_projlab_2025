import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public IOPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(51, 70, 95));
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
        growMushroomBodyButton.setIcon(new ImageIcon("growstring.png"))
        ;
        branchStringButton = createButton("Fonál ágaztatás");
        branchStringButton.setIcon(new ImageIcon("branch.png"));

        growMushroomBodyButton = createButton("Gombatest növesztés");
        growMushroomBodyButton.setIcon(new ImageIcon("growbody.png"));

        eatInsectsButton = createButton("Rovar evés");
        scatterSporesButton = createButton("Spóra szórás");

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
    }

    // Eseménykezelők beállítása
    public void setCutAction(ActionListener l) { cutButton.addActionListener(l); }
    public void setConsumeAction(ActionListener l) { consumeButton.addActionListener(l); }
    public void setMoveAction(ActionListener l) { moveButton.addActionListener(l); }
    public void setEndTurnAction(ActionListener l) { endTurnButton.addActionListener(l); }

    public void setGrowStringAction(ActionListener l) { growStringButton.addActionListener(l); }
    public void setBranchStringAction(ActionListener l) { branchStringButton.addActionListener(l); }
    public void setGrowMushroomBodyAction(ActionListener l) { growMushroomBodyButton.addActionListener(l); }
    public void setEatInsectsAction(ActionListener l) { eatInsectsButton.addActionListener(l); }
    public void setscatterSporesAction(ActionListener l) { scatterSporesButton.addActionListener(l); }
}

