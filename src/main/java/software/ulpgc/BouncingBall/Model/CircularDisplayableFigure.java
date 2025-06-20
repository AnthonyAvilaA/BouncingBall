package software.ulpgc.BouncingBall.Model;

public interface CircularDisplayableFigure {
    Vector2D position = null;
    int radius = 0;

    Vector2D position();
    int radius();
}
