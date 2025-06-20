package software.ulpgc.BouncingBall.Model;

import java.awt.*;

public record Ball (
        Vector2D position,
        Vector2D velocity,
        Vector2D acceleration,
        float restitution,
        int radius,
        int mass) implements CircularDisplayableFigure {

    public Ball() {
        this(
                new Vector2D(15,0),
                new Vector2D(0,0),
                new Vector2D(0,9.81*10),
                0.35f,
                10,
                10);
    }

    @Override
    public String toString() {
        return "Ball{" +
                "position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                ", restitution=" + restitution +
                ", radius=" + radius +
                ", mass=" + mass +
                '}';
    }
}

