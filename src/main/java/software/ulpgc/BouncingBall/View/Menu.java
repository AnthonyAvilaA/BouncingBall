package software.ulpgc.BouncingBall.View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Menu extends JPanel {
    private HashMap<String, JTextField> fields = new HashMap<>();
    private final JPanel fieldsPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    public Menu() {
        this.setLayout(new FlowLayout());

        this.buttonPanel.add(new JButton("Create"));

        this.add(fieldsPanel);
        this.add(buttonPanel);
    }

    public void addField(String name, int value) {
        this.add(new JLabel(name + ": "));

        JTextField textField = new JTextField(value);
        textField.setColumns(10);

        this.fieldsPanel.add(textField);
        this.fields.put(name, textField);

    }


}
