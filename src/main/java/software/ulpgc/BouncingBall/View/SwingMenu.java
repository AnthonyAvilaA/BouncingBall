package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SwingMenu extends JPanel {
    private final List<SwingBallField> ballFieldList = new LinkedList<>();
    private final JPanel ballsPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    public SwingMenu() {
        this.setLayout(new FlowLayout());

        this.add(ballsPanel);
        this.add(buttonPanel);
    }

    public void addBall() {
        SwingBallField ballField = new SwingBallField();
        ballField.setRemoveButton(getBallDisplayRemoveButton(ballField));
        this.ballFieldList.add(ballField);
        this.ballsPanel.add(ballField);
    }

    private JButton getBallDisplayRemoveButton(SwingBallField ballField) {
        JButton button = new JButton("Remove");
        button.setBackground(Color.red);
        button.addActionListener(e -> {
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
        this.buttonPanel.add(button);
    }
}
