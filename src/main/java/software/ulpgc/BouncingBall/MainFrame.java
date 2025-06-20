package software.ulpgc.BouncingBall;

import software.ulpgc.BouncingBall.Control.Command;
import software.ulpgc.BouncingBall.View.ContentDisplay;
import software.ulpgc.BouncingBall.View.SwingMenu;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final HashMap<String, Command> commands;
    private SwingMenu menu;

    public MainFrame(ContentDisplay display) throws HeadlessException {
        this.commands = new HashMap<>();

        this.setTitle("Bouncing Ball");
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.add(display, BorderLayout.CENTER);
        this.add(getMenu(), BorderLayout.NORTH);
    }

    public void addCommand(String name, Command command) {
        this.commands.put(name, command);
    }

    private JPanel getMenu() {
        SwingMenu menu = new SwingMenu();
        menu.addBallField();
        JButton addButton = new JButton("add");
        addButton.setBackground(Color.green);
        addButton.addActionListener(
                e -> this.commands.get("add").execute(null));
        menu.addButton(addButton);

        JButton createButton = new JButton("Create");
        createButton.setBackground(Color.yellow);
        createButton.addActionListener(
                e -> this.commands.get("start").execute(menu.getBalls()));
        menu.addButton(createButton);

        this.menu = menu;
        return menu;
    }

    public void addBall() {
        this.menu.addBallField();
    }
}
