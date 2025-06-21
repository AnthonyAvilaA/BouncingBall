package software.ulpgc.BouncingBall.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Ball (
        Vector2D position,
        Vector2D velocity,
        Vector2D acceleration,
        float restitution,
        int radius,
        int mass,
        String color) implements CircularDisplayableFigure {

    public Ball() {
        this(
            new Vector2D(15,0),
            new Vector2D(0,0),
            new Vector2D(0, BigDecimal.valueOf(9.81 * 10).setScale(6, RoundingMode.HALF_UP).doubleValue()),
            1f,
            10,
            10,
            "ff5a37"
        );
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

