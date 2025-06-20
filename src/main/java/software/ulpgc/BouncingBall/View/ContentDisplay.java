package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.CircularDisplayableFigure;
import software.ulpgc.BouncingBall.Model.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class ContentDisplay extends JPanel implements BallDisplay {

    private List<CircularDisplayableFigure> objectsToDisplay = new LinkedList<>();

    public ContentDisplay() {
        this.setBackground(Color.WHITE);
    }

    @Override
    public void display(List<CircularDisplayableFigure> objectsToDisplay) {
        this.objectsToDisplay = objectsToDisplay;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.removeAll();
        this.objectsToDisplay.forEach(o -> drawBall(g, o));
        revalidate();
    }

    private void drawBall(Graphics g, CircularDisplayableFigure objectToDisplay) {
        Vector2D position = objectToDisplay.position();
        int radius = objectToDisplay.radius();

        Dimension screenSize = this.getScreenSize();
        Vector2D center = new Vector2D(screenSize.width, screenSize.height).divisionByScalar(2);

        if (objectToDisplay instanceof Ball) {
            g.fillOval((int) position.x() - radius + (int) center.x(),
                    (int) position.y() - radius + (int) center.y(),
                    radius * 2,
                    radius * 2
            );
        } else {
            g.drawOval((int) position.x() - radius + (int) center.x(),
                    (int) position.y() - radius + (int) center.y(),
                    radius * 2,
                    radius * 2
            );
        }
    }

    @Override
    public Dimension getScreenSize() {
        return this.getSize();
    }
}
