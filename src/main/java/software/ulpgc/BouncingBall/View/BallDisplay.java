package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Vector2D;

import java.awt.*;

public interface BallDisplay {
    void display(Ball ball);
    void clear();

    Dimension getScreenSize();
}
