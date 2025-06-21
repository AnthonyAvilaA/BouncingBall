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
        double restitution = ball.restitution();

        // 4. Calculate bounce with strict damping
        double normalVelocity = ball.velocity().dotProduct(normal);
        Vector2D vNormal = normal.productByScalar(-(restitution) * normalVelocity);
        Vector2D vTangent = ball.velocity().subtract(normal.productByScalar(normalVelocity));
        Vector2D newVelocity = vTangent.addition(vNormal);

        if (newVelocity.module() < 0.1) {
            newVelocity = new Vector2D(0, 0);
        }

        // 5. Conservative position correction
        double penetration = ball.radius() + circle.radius() - distance;

        if (penetration < 0.01) penetration = 0;

        double maxFix = 5.0; // Very strict limit
        penetration = Math.max(-maxFix, Math.min(maxFix, penetration));
        Vector2D newPosition = ball.position().addition(normal.productByScalar(penetration));

        // 6. Hard reset if values are unreasonable
        if (Math.abs(newPosition.y()) > 1_000 ||
                Double.isNaN(newPosition.x())) {
            newPosition = new Vector2D(0, circle.radius() + ball.radius() + 10);
            newVelocity = new Vector2D(0, 0);
        }

        if (Math.abs(newVelocity.x()) < 1e-3) newVelocity = new Vector2D(0, newVelocity.y());
        if (Math.abs(newVelocity.y()) < 1e-3) newVelocity = new Vector2D(newVelocity.x(), 0);

        if (!isValidPosition(newPosition) || isOutsideCircle(newPosition)) {
            newPosition = newPosition.normalize().productByScalar(circle.radius() - ball.radius());
        }

        if (isOutsideCircle(newPosition)) {
            newPosition = newPosition.normalize().productByScalar(circle.radius() - ball.radius());
        }

        return new Ball(
                newPosition,
                newVelocity,
                ball.acceleration(),
                ball.restitution(),
                ball.radius(),
                ball.mass(),
                ball.color()
        );
    }

    private boolean isOutsideCircle(Vector2D position) {
        return position.squaredModule() > Math.pow(circle.radius() - ball.radius(), 2);
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