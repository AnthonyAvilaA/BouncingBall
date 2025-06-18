package software.ulpgc.BouncingBall;

import software.ulpgc.BouncingBall.Control.Command;
import software.ulpgc.BouncingBall.View.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final HashMap<String, Command> commands;

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
        Menu menu = new Menu();
        menu.addField("Balls number", 1);
        return menu;
    }
}
