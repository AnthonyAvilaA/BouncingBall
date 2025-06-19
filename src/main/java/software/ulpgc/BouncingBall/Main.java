package software.ulpgc.BouncingBall;

import software.ulpgc.BouncingBall.Control.ContentPresenter;
import software.ulpgc.BouncingBall.Control.StartCommand;
import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Circle;
import software.ulpgc.BouncingBall.View.ContentDisplay;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainFrame mainframe = new MainFrame();
        ContentDisplay display = new ContentDisplay();
        ContentPresenter contentPresenter = new ContentPresenter(display, List.of(), new Circle());
        mainframe.addCommand("Start", new StartCommand(contentPresenter));
        mainframe.setVisible(true);
    }
}
