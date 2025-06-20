package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.CircularDisplayableFigure;
import software.ulpgc.BouncingBall.Model.Vector2D;

import java.awt.*;
import java.util.List;

public interface BallDisplay {
    void display(List<CircularDisplayableFigure> objectsToDisplay);

    Dimension getScreenSize();
}
