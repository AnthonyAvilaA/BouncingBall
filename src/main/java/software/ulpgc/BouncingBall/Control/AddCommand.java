package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.MainFrame;
import software.ulpgc.BouncingBall.Model.Ball;

import java.util.List;

public class AddCommand implements Command {
    private final MainFrame mainFrame;

    public AddCommand(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void execute(Object object) {
        mainFrame.addBall();
    }
}
