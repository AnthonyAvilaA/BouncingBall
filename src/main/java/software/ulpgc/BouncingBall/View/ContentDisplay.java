package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Vector2D;

import javax.swing.*;
import java.awt.*;

public class ContentDisplay extends JPanel implements BallDisplay {

    private Ball currentBall;

    public ContentDisplay() {
        this.setBackground(Color.WHITE);
    }

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
        if (this.currentBall != null) {
            drawCircle(g);
        }
        revalidate();
    }

    private void drawCircle(Graphics g) {
        Vector2D position = this.currentBall.position();
        int radius = this.currentBall.radius();

        Dimension screenSize = this.getScreenSize();
        Vector2D center = new Vector2D(screenSize.width, screenSize.height).divisionByScalar(2);

        g.fillOval((int) position.x() - radius + (int) center.x(),
                (int) position.y() - radius + (int) center.y(),
                radius * 2,
                radius * 2
        );
    }

    @Override
    public Dimension getScreenSize() {
        return this.getSize();
    }
}
