package software.ulpgc.BouncingBall;

import software.ulpgc.BouncingBall.Control.Command;
import software.ulpgc.BouncingBall.View.SwingMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final HashMap<String, Command> commands;
    private SwingMenu menu;

    public MainFrame() throws HeadlessException {
        this.commands = new HashMap<>();

        this.setTitle("Bouncing Ball");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.add(getMenu(), BorderLayout.NORTH);
        //this.add(getDisplay(), BorderLayout.CENTER);
    }

    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }

    private JPanel getMenu() {
        SwingMenu menu = new SwingMenu();
        menu.addBall();
        JButton createButton = new JButton("Create");
        createButton.addActionListener(
                e -> this.commands.get("create").execute(menu.getBalls()));
        menu.addButton(createButton);

        this.menu = menu;
        return menu;
    }
}
