package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.Model.*;
import software.ulpgc.BouncingBall.View.ContentDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContentPresenter {
    private final ContentDisplay contentDisplay;
    private ScheduledExecutorService scheduler;
    private List<Ball> ballList;
    private final Circle circle;
    private double startTime;

    public ContentPresenter(ContentDisplay contentDisplay, List<Ball> ballList, Circle circle) {
        this.contentDisplay = contentDisplay;
        this.circle = circle;
        startTime = System.nanoTime();
    }

    public void scheduleTask(List<Ball> ballList) {
        this.ballList = ballList;
        initializeSchedule();

        Runnable update = () -> {
            this.ballList = getNewBallPosition();
            displayBalls(ballList);
            startTime = System.nanoTime();
        };

        int period = 1000 / 60;
        scheduler.scheduleAtFixedRate(update, 0, period, TimeUnit.MILLISECONDS);
    }

    private void displayBalls(List<Ball> ballList) {
        ballList.forEach(this.contentDisplay::display);
    }

    private List<Ball> getNewBallPosition() {
        List<Ball> newBallList = new ArrayList<>(this.ballList.size());
        List<Ball> checkCollisionBallsList = new ArrayList<>(List.copyOf(this.ballList));
        while (!ballList.isEmpty()) {
            Ball currentBall = checkCollisionBallsList.removeFirst();

            for (Ball otherBall : checkCollisionBallsList) {
                BallBallCollisionResolver collisionResolver = new BallBallCollisionResolver(currentBall, otherBall);
                if (collisionResolver.isColliding()) {
                    checkCollisionBallsList.remove(collisionResolver.newBalls()[1]);
                    newBallList.add(updateBall(collisionResolver.newBalls()[1]));
                    newBallList.add(updateBall(currentBall));
                    break;
                }
            }
            newBallList.add(updateBall(currentBall));
        }
        return newBallList;
    }

    private Ball updateBall(Ball ball) {
        BallCircleCollisionResolver collisionResolver = new BallCircleCollisionResolver(ball, this.circle);
        Ball resultBall = collisionResolver.getNewBall();
        double deltaTime = System.nanoTime() - startTime;
        Vector2D velocity = ball.acceleration().productByScalar(deltaTime);
        Vector2D position = ball.position().addition(velocity.productByScalar(deltaTime));
        return new Ball(
                position,
                velocity,
                ball.acceleration(),
                ball.restitution(),
                ball.radius(),
                ball.mass()
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
