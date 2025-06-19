package software.ulpgc.BouncingBall.Model;

public record Circle(int radius) {
    public Circle() {
        this(20);
    }
}
