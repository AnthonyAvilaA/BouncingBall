package software.ulpgc.BouncingBall.Model;

public class BallCircleCollisionResolver {
    private final boolean isColliding;
    private final Ball ball;
    private final Circle circle;
    private Ball newBall;

    public BallCircleCollisionResolver(Ball ball, Circle circle) {
        this.ball = ball;
        this.circle = circle;

        double distance = ball.position().squaredModule();

        this.isColliding = checkCollision(distance);
        if (isColliding) {
            this.newBall = getNewBall(distance);
        }
    }

    private Boolean checkCollision(double distance) {
       return distance > Math.pow(this.circle.radius() - this.ball.radius(), 2);
    }

    private Ball getNewBall(double distance) {
        Vector2D normal = ball.position().divisionByScalar(distance);
        Double normalVelocity = ball.velocity().dotProduct(normal);
        double restitutionValue = (1 + ball.restitution()) * normalVelocity;
        Vector2D velocity = ball.velocity().subtraction(normal.productByScalar(restitutionValue));

        double overlapped = distance + ball.radius() - circle.radius();
        Vector2D position = ball.position().subtraction(normal.productByScalar(overlapped));

        return new Ball(
                position,
                velocity,
                ball.acceleration(),
                ball.restitution(),
                ball.radius(),
                ball.mass()
        );
    }

    public boolean isColliding() {
        return this.isColliding;
    }

    public Ball getNewBall() {
        if (!this.isColliding) return this.ball;
        return this.newBall;
    }

}