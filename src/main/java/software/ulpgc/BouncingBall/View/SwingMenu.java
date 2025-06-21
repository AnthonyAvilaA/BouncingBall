package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Circle;
import software.ulpgc.BouncingBall.Model.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SwingMenu extends JPanel {
    private final List<SwingBallField> ballFieldList = new LinkedList<>();
    private final JPanel ballsPanel = new JPanel();
    private final JPanel topPanel = new JPanel();

    private final JTextField speedPanel;
    private final JTextField circleRadiusPanel;
    private final Button hide;
    private final JTextField fpsPanel;


    public SwingMenu() {
        this.setLayout(new BorderLayout());

        this.ballsPanel.setLayout(new BoxLayout(this.ballsPanel, BoxLayout.Y_AXIS));

        topPanel.add(new JLabel("fps:"));
        this.fpsPanel = new JTextField("60", 5);
        topPanel.add(this.fpsPanel);

        topPanel.add(new JLabel("Speed:"));
        this.speedPanel = new JTextField("1.0", 5);
        topPanel.add(this.speedPanel);

        topPanel.add(new JLabel("Circle radius:"));
        this.circleRadiusPanel = new JTextField(String.valueOf(new Circle().radius()), 5);
        topPanel.add(this.circleRadiusPanel);

        this.hide = new Button("Hide");
        this.hide.setBackground(Color.gray);
        this.hide.addActionListener(_ -> {
            if (this.ballsPanel.isVisible()) {
                this.ballsPanel.setVisible(false);
                this.hide.setLabel("Show");
            } else {
                this.ballsPanel.setVisible(true);
                this.hide.setLabel("Hide");
            }
        });
        topPanel.add(this.hide);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(ballsPanel, BorderLayout.CENTER);
    }

    public void addBallField() {
        SwingBallField ballField = new SwingBallField();
        ballField.setRemoveButton(getBallDisplayRemoveButton(ballField));
        this.ballFieldList.add(ballField);
        this.ballsPanel.add(ballField);

        this.ballsPanel.revalidate();
        this.ballsPanel.repaint();
    }

    private JButton getBallDisplayRemoveButton(SwingBallField ballField) {
        JButton button = new JButton("Remove");
        button.setBackground(Color.red);
        button.addActionListener(_ -> {
            this.ballFieldList.remove(ballField);
            revalidateBallsPanel();
        });
        return button;
    }

    public List<Ball> getBalls() {
        List<Ball> result = new ArrayList<>(this.ballFieldList.size());
        for (SwingBallField field: this.ballFieldList) {
            result.add(field.getBall());
        }
        return result;
    }

    private void revalidateBallsPanel() {
        this.ballsPanel.removeAll();
        this.ballFieldList.forEach(this.ballsPanel::add);
    }

    public void addButton(JButton button) {
        this.topPanel.add(button);
    }

    public float getSpeed() {
        try {
            return Float.parseFloat(this.speedPanel.getText());
        } catch (NumberFormatException e) {
            return 1.0f; // Default speed if parsing fails
        }
    }

    public int getFps() {
        try {
            return Integer.parseInt(this.fpsPanel.getText());
        } catch (NumberFormatException e) {
            return 60; // Default speed if parsing fails
        }
    }

    public Circle getCircle() {
        try {
            int radius = Integer.parseInt(this.circleRadiusPanel.getText());
            return new Circle(
                    radius,
                    new Vector2D(0,0)
            );
        } catch (NumberFormatException e) {
            this.circleRadiusPanel.setText(String.valueOf(new Circle().radius()));
            return new Circle(); // Default radius if parsing fails
        }
    }
}
