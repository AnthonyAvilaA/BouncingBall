package software.ulpgc.BouncingBall.Model;

public record Circle(int radius, int x, int y) {
    public Circle() {
        this(120, 0, 0);
    }
}
