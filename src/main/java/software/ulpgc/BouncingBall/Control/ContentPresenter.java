package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.Model.*;
import software.ulpgc.BouncingBall.View.ContentDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContentPresenter {
    private final ContentDisplay contentDisplay;
    private ScheduledExecutorService scheduler;
    private List<Ball> ballList;
    private final List<CircularDisplayableFigure> displayList = new LinkedList<>();
    private final Circle circle;
    private double startTime;

    public ContentPresenter(ContentDisplay contentDisplay, Circle circle) {
        this.contentDisplay = contentDisplay;
        this.circle = circle;
        startTime = System.nanoTime();
    }

    public void scheduleTask(List<Ball> ballList) {
        this.ballList = ballList;
        initializeSchedule();

        Runnable update = () -> {

            this.ballList = getNewBallPosition();

            this.displayList.clear();
            this.displayList.addAll(this.ballList);
            System.out.println(this.displayList);
            this.displayList.add(this.circle);

            this.contentDisplay.display(this.displayList);
            startTime = System.nanoTime();
        };

        int period = 1000 / 60;
        scheduler.scheduleAtFixedRate(update, 0, period, TimeUnit.MILLISECONDS);

    }

    private List<Ball> getNewBallPosition() {
        List<Ball> newBallList = new ArrayList<>(this.ballList.size());
        for (Ball currentBall: this.ballList){
            boolean collided = false;

            for (Ball otherBall : this.ballList) {
                if (currentBall.equals(otherBall)) continue;
                BallBallCollisionResolver collisionResolver = new BallBallCollisionResolver(currentBall, otherBall);
                if (collisionResolver.isColliding()) {
                    //checkCollisionBallsList.remove(collisionResolver.getOriginalBalls()[1]);
                    //newBallList.add(updateBall(collisionResolver.newBalls()[1]));
                    newBallList.add(updateBall(collisionResolver.newBalls()[0]));
                    collided = true;
                    break;
                }
            }
            if (!collided) newBallList.add(updateBall(currentBall));
        }
        return newBallList;
    }

    private Ball updateBall(Ball ball) {
        BallCircleCollisionResolver collisionResolver = new BallCircleCollisionResolver(ball, this.circle);
        Ball resultBall = collisionResolver.getNewBall();

        double deltaTime = (System.nanoTime() - startTime) / 1_000_000_000.0; // Seconds

        Vector2D newVelocity = resultBall.velocity().addition(
                resultBall.acceleration().productByScalar(deltaTime)
        );

        Vector2D newPosition = resultBall.position().addition(
                newVelocity.productByScalar(deltaTime)
        );

        return new Ball(
                newPosition,
                newVelocity,
                resultBall.acceleration(), // Keep acceleration unchanged (or modify if needed)
                resultBall.restitution(),
                resultBall.radius(),
                resultBall.mass()
        );
    }

    private void initializeSchedule() {
        schedulerShutdown();
        startTime = System.nanoTime();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    private void schedulerShutdown() {
        if (scheduler != null) scheduler.shutdown();
    }

}
