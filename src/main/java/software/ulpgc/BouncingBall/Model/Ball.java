package software.ulpgc.BouncingBall.Model;

public record Ball (
        Vector2D position,
        Vector2D velocity,
        Vector2D acceleration,
        float restitution,
        int radius,
        int mass) {

    public Ball() {
        this(
                new Vector2D(1,1),
                new Vector2D(0,0),
                new Vector2D(0,0),
                0.5f,
                5,
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

