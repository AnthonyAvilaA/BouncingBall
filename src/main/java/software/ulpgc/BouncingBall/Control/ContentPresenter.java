package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.MainFrame;
import software.ulpgc.BouncingBall.Model.*;
import software.ulpgc.BouncingBall.View.ContentDisplay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContentPresenter {
    private final ContentDisplay contentDisplay;
    private ScheduledExecutorService scheduler;
    private final List<CircularDisplayableFigure> displayList = new LinkedList<>();
    private List<Ball> ballList;
    private double startTime;
    private final MainFrame mainframe;
    private Circle circle;
    private float speed = 1.0f;
    private int fps = 60;

    public ContentPresenter(MainFrame mainframe, ContentDisplay contentDisplay, Circle circle) {
        this.contentDisplay = contentDisplay;
        this.circle = circle;
        startTime = System.nanoTime();
        this.mainframe = mainframe;
    }

    public void scheduleTask(List<Ball> ballList) {
        this.ballList = ballList;
        this.circle = mainframe.getCircle();
        this.speed = this.mainframe.getSpeed();
        this.fps = this.mainframe.getFps();
        initializeSchedule();

        Runnable update = () -> {

            this.ballList = getNewBallPosition();

            this.displayList.clear();
            this.displayList.addAll(this.ballList);
            this.displayList.add(this.circle);

            this.contentDisplay.display(this.displayList);
            startTime = System.nanoTime();
        };

        int period = 1000 / this.fps;
        scheduler.scheduleAtFixedRate(update, 0, period, TimeUnit.MILLISECONDS);

    }

    private List<Ball> getNewBallPosition() {
        List<Ball> newBallList = new ArrayList<>(this.ballList.size());

        // TODO : improve performance by avoiding redoing all collisions

        for (Ball currentBall: this.ballList){
            boolean collided = false;

            for (Ball otherBall : this.ballList) {
                if (currentBall.equals(otherBall)) continue;
                BallBallCollisionResolver collisionResolver = new BallBallCollisionResolver(currentBall, otherBall);
                if (collisionResolver.isColliding()) {
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

        double deltaTime = (System.nanoTime() - startTime) / 1_000_000_000.0 * this.speed; // Seconds

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
                resultBall.mass(),
                resultBall.color()
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
