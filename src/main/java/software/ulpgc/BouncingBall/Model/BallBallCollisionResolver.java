package software.ulpgc.BouncingBall.Model;


public class BallBallCollisionResolver {
    private final boolean isColliding;
    private final Ball ball1;
    private final Ball ball2;
    private final Ball[] newBalls = new Ball[2];

    public BallBallCollisionResolver(Ball ball1, Ball ball2) {
        this.ball1 = ball1;
        this.ball2 = ball2;

        Vector2D positionDifference = ball1.position().subtract(ball2.position());
        double distance = positionDifference.module();

        this.isColliding = checkCollision(distance);
        if (isColliding) {
            getNewBalls(positionDifference, distance);
        } else {
            this.newBalls[0] = ball1;
            this.newBalls[1] = ball2;
        }
    }

    private Boolean checkCollision(double distance) {
       return distance <= ball1.radius() + ball2.radius();
    }

    private void getNewBalls(Vector2D positionDifference, double distance) {
        if (distance < 0.0001) {  // Small threshold to avoid division by zero
            // Separate balls slightly to avoid NaN
            Vector2D slightOffset = new Vector2D(0.1, 0.1);
            newBalls[0] = new Ball(
                    ball1.position().addition(slightOffset),
                    ball1.velocity(),
                    ball1.acceleration(),
                    ball1.restitution(),
                    ball1.radius(),
                    ball1.mass(),
                    ball1.color()
            );
            newBalls[1] = new Ball(
                    ball2.position().subtract(slightOffset),
                    ball2.velocity(),
                    ball2.acceleration(),
                    ball2.restitution(),
                    ball2.radius(),
                    ball2.mass(),
                    ball2.color()
            );
            return;
        }

        // 3. Normal vector calculation with validation
        Vector2D normal;
        try {
            normal = positionDifference.divisionByScalar(distance);
        } catch (ArithmeticException e) {
            normal = new Vector2D(1, 0);  // Fallback normal
        }

        // 4. Relative velocity calculation
        Vector2D relativeVel = ball1.velocity().subtract(ball2.velocity());
        double velocityAlongNormal = relativeVel.dotProduct(normal);

        // 5. Early exit if moving apart
        if (velocityAlongNormal > 0) {
            newBalls[0] = ball1;
            newBalls[1] = ball2;
            return;
        }

        // 6. Impulse calculation with validation
        double combinedRestitution = Math.sqrt(ball1.restitution() * ball2.restitution());
        double impulseScalar = -(1 + combinedRestitution) * velocityAlongNormal;
        impulseScalar /= ((double) 1 /ball1.mass() + (double) 1 /ball2.mass());

        Vector2D impulse = normal.productByScalar(impulseScalar);

        // 7. Position correction with limits
        double overlap = (ball1.radius() + ball2.radius()) - distance;
        if (overlap < 0.01) overlap = 0;

        double correctionPercent = 0.1;
        Vector2D correction = normal.productByScalar(overlap * correctionPercent);

        // 8. Create new balls with validation
        Vector2D newPos1 = ball1.position().addition(correction);
        Vector2D newPos2 = ball2.position().subtract(correction);


        newBalls[0] = new Ball(
                newPos1,
                ball1.velocity().addition(impulse.divisionByScalar(ball1.mass())),
                ball1.acceleration(),
                ball1.restitution(),
                ball1.radius(),
                ball1.mass(),
                ball1.color()
        );

        newBalls[1] = new Ball(
                newPos2,
                ball2.velocity().subtract(impulse.divisionByScalar(ball2.mass())),
                ball2.acceleration(),
                ball2.restitution(),
                ball2.radius(),
                ball2.mass(),
                ball2.color()
        );

    }



    public boolean isColliding() {
        return this.isColliding;
    }

    public Ball[] getOriginalBalls() {
        Ball[] result = new Ball[2];
        result[0] = ball1;
        result[1] = ball2;
        return result;
    }

    public Ball[] newBalls() {
        return this.newBalls;
    }

}