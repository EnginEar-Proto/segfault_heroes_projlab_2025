import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamSelectionPanel extends JPanel {
    /**
     * Konstruktor a csapat kiválasztó panel létrehozásához.
     */
    public TeamSelectionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 250));
        setMaximumSize(new Dimension(300, 250));

        add(Box.createRigidArea(new Dimension(0, 20)));
    }

    /**
     * Új játék indítása esetén, a player nevek felhasználótól való bekérésére szolgál.
     * Lérehoz egy új JPanel-t a képernyő közepén, benne JTextFieldekkel.
     * A bekért neveknek nem üresnek, és egymástól különbözőnek kell lenniük,
     * ha az ezek nem teljesülnek, újbóli beírást kér a felhasználótól.
     * Ha cancel-t nyom a felhasználó, akkor null-t ad vissza.
     * Ha sikeres a beírás, visszadja a playerek neveit tartalmazó tömböt.
     *
     * @param frame A frame, amelyen megjelenik.
     * @return Stringek tömbje, mely tartalmazza a playerek nevét.
     */
    public String[] requestPlayerNames(JFrame frame, int teamNumber) {
        JLabel titleLabel = new JLabel("Az " + teamNumber + ". csapat adatai");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel teamLabel = new JLabel("Csapatnév:");
        JTextField teamField = new JTextField();
        JLabel mushroomerLabel = new JLabel("Mushroomer:");
        JTextField mushroomerField = new JTextField();
        JLabel insecterLabel = new JLabel("Insecter:");
        JTextField insecterField = new JTextField();

        add(titleLabel);
        add(teamLabel);
        add(teamField);
        add(mushroomerLabel);
        add(mushroomerField);
        add(insecterLabel);
        add(insecterField);

        while (true) {
            int result = JOptionPane.showConfirmDialog(this, this, "Enter Team and Player Names", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            String teamName = teamField.getText();
            String mushroomer = mushroomerField.getText().trim();
            String insecter = insecterField.getText().trim();
            remove(teamLabel);
            remove(teamField);
            remove(mushroomerLabel);
            remove(mushroomerField);
            remove(insecterLabel);
            remove(insecterField);
            remove(titleLabel);
            return new String[]{teamName, mushroomer, insecter};
        }
    }
}
