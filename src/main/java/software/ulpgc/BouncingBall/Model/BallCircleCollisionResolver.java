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
        return distance > Math.pow((this.circle.radius() - this.ball.radius()), 2);
    }

    private Ball getNewBall(double distance) {
        // 1. Verify distance is plausible
        if (distance > 1_000_000 || Double.isInfinite(distance)) {
            distance = circle.radius() + ball.radius();
        }

        // 2. Calculate normal (safe fallback)
        Vector2D circleCenter = new Vector2D(0, 0);
        Vector2D normal;
        try {
            normal = ball.position().subtract(circleCenter).normalize();
        } catch (Exception e) {
            normal = new Vector2D(0, 1);
        }

        // 3. Limit restitution effects
        double restitution = Math.min(0.8, Math.max(0.3, ball.restitution()));

        // 4. Calculate bounce with strict damping
        double normalVelocity = ball.velocity().dotProduct(normal);
        Vector2D newVelocity = ball.velocity()
                .subtract(normal.productByScalar((1 + restitution) * normalVelocity))
                .productByScalar(0.7); // Strong damping

        // 5. Conservative position correction
        double penetration = ball.radius() + circle.radius() - distance;
        double maxFix = 5.0; // Very strict limit
        penetration = Math.max(-maxFix, Math.min(maxFix, penetration));
        Vector2D newPosition = ball.position().addition(normal.productByScalar(penetration));

        // 6. Hard reset if values are unreasonable
        if (Math.abs(newPosition.y()) > 1_000 ||
                Double.isNaN(newPosition.x())) {
            newPosition = new Vector2D(0, circle.radius() + ball.radius() + 10);
            newVelocity = new Vector2D(0, 0);
        }

        System.out.printf(
                "Collision: dist=%.2f | vel=%s | norm=%s | pen=%.2f%n",
                distance, ball.velocity(), normal, penetration
        );

        return new Ball(
                newPosition,
                newVelocity,
                ball.acceleration(),
                (float) restitution,
                ball.radius(),
                ball.mass()
        );
    }

    private boolean isValidPosition(Vector2D pos) {
        return !Double.isNaN(pos.x()) && !Double.isNaN(pos.y())
                && !Double.isInfinite(pos.x()) && !Double.isInfinite(pos.y());
    }

    public boolean isColliding() {
        return this.isColliding;
    }

    public Ball getNewBall() {
        if (!this.isColliding) return this.ball;
        return this.newBall;
    }

}