package software.ulpgc.BouncingBall;

import software.ulpgc.BouncingBall.Control.AddCommand;
import software.ulpgc.BouncingBall.Control.ContentPresenter;
import software.ulpgc.BouncingBall.Control.StartCommand;
import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Circle;
import software.ulpgc.BouncingBall.View.ContentDisplay;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ContentDisplay display = new ContentDisplay();
        MainFrame mainframe = new MainFrame(display);
        ContentPresenter contentPresenter = new ContentPresenter(display, new Circle());
        mainframe.addCommand("start", new StartCommand(contentPresenter));
        mainframe.addCommand("add", new AddCommand(mainframe));
        mainframe.setVisible(true);
    }
}
