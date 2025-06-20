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
        double distance = positionDifference.squaredModule();

        this.isColliding = checkCollision(distance);
        if (isColliding) {
            getNewBalls(positionDifference, distance);
        } else {
            this.newBalls[0] = ball1;
            this.newBalls[1] = ball2;
        }
    }

    private Boolean checkCollision(double distance) {
        int radiusSum = ball1.radius() + ball2.radius();
       return distance <= (radiusSum * radiusSum);
    }

    private void getNewBalls(Vector2D positionDifference, double distance) {
        Vector2D normal = positionDifference.divisionByScalar(distance);
        Vector2D relativeVel = ball1.velocity().subtract(ball2.velocity());
        double velocityAlongNormal = relativeVel.dotProduct(normal);

        if (velocityAlongNormal > 0) {
            // if balls are moving away
            newBalls[0] = ball1;
            newBalls[1] = ball2;
            return;
        }

        // restitution is the bounding coefficient
        // if the balls has different values of it, then the value is used is close to the min
        double combinedRestitution = Math.min(ball1.restitution(), ball2.restitution());
        // A better approach would be the squared root, up to you to uncomment this line
        // double combinedRestitution = Math.sqrt(ball1.restitution()*ball1.restitution());

        double impulseMagnitude = -(1 + combinedRestitution) * velocityAlongNormal;
        impulseMagnitude /= (double) 1 / ball1.mass() + (double) 1 / ball2.mass();

        Vector2D impulse = normal.productByScalar(impulseMagnitude);

        // if balls have been overlapped (optional)
        double overlap = ball1.radius() + ball2.radius() - distance;
        double correctionPercent = 0.5;

        newBalls[0] = new Ball(
                normal.productByScalar(overlap * correctionPercent),
                impulse.divisionByScalar(ball1.mass()),
                ball1.acceleration(),
                ball1.restitution(),
                ball1.radius(),
                ball1.mass()
        );

        newBalls[1] = new Ball(
                normal.productByScalar(overlap * correctionPercent),
                impulse.divisionByScalar(ball2.mass()),
                ball2.acceleration(),
                ball2.restitution(),
                ball2.radius(),
                ball2.mass()
        );

    }

    public boolean isColliding() {
        return this.isColliding;
    }

    public Ball[] getOriginalBalls() throws Exception {
        Ball[] result = new Ball[2];
        result[0] = ball1;
        result[1] = ball2;
        return result;
    }

    public Ball[] newBalls() {
        return this.newBalls;
    }

}