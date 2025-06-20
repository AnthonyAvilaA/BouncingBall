package software.ulpgc.BouncingBall.Model;

public record Circle(int radius, Vector2D position) implements CircularDisplayableFigure {
    public Circle() {
        this(120, new Vector2D(0, 0));
    }
}
