package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Vector2D;

import javax.swing.*;
import java.awt.*;

public class ContentDisplay extends JPanel implements BallDisplay {

    private Ball currentBall;

    @Override
    public void display(Ball ball) {
        currentBall = ball;
        repaint();
    }

    @Override
    public void clear() {
        removeAll();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        drawCircle(g);
        revalidate();
    }

    private void drawCircle(Graphics g) {
        Vector2D position = this.currentBall.position();
        int radius = this.currentBall.radius();

        g.fillOval((int) position.x() - radius,
                (int) position.y() - radius,
                radius * 2,
                radius * 2
        );
    }

    @Override
    public Dimension getScreenSize() {
        return this.getSize();
    }
}
