package software.ulpgc.BouncingBall.View;

import software.ulpgc.BouncingBall.Model.Ball;
import software.ulpgc.BouncingBall.Model.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SwingBallField extends JPanel {

    private final HashMap<String, JTextField> fields = new HashMap<>();

    public SwingBallField() {
        this.setLayout(new FlowLayout());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, this.getSize().height);

        Ball defaultBall = new Ball();
        Vector2D defaultPosition = defaultBall.position();
        Vector2D defaultVelocity = defaultBall.velocity();
        Vector2D defaultAcceleration = defaultBall.acceleration();
        float defaultRestitution = defaultBall.restitution();
        int defaultMass = defaultBall.mass();
        int defaultRadius = defaultBall.radius();
        String defaultColor = defaultBall.color();

        this.add(getField("position", String.valueOf(defaultPosition.x()), String.valueOf(defaultPosition.y())));
        this.add(getField("velocity", String.valueOf(defaultVelocity.x()), String.valueOf(defaultVelocity.y())));
        this.add(getField("acceleration", String.valueOf(defaultAcceleration.x()), String.valueOf(defaultAcceleration.y())));
        this.add(getField("restitution", String.valueOf(defaultRestitution)));
        this.add(getField("mass", String.valueOf(defaultMass)));
        this.add(getField("radius", String.valueOf(defaultRadius)));
        this.add(getField("color", defaultColor));
    }

    public void setRemoveButton(JButton button) {
        this.add(button);
    }

    private JPanel getField(String name, String value1, String value2) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(name));

        JTextField textField1 = new JTextField(value1);
        panel.add(name+"X", textField1);
        this.fields.put(name+"X", textField1);

        JTextField textField2 = new JTextField(value2);
        panel.add(name+"Y", textField2);
        this.fields.put(name+"Y", textField2);

        return panel;
    }

    private JPanel getField(String name, String value) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(name));

        JTextField textField = new JTextField(value);
        panel.add(textField);
        this.fields.put(name, textField);

        return panel;
    }

    public Ball getBall() {
        Vector2D position = getVector2DOf("position");
        Vector2D velocity = getVector2DOf("velocity");
        Vector2D acceleration = getVector2DOf("acceleration");
        float restitution = Float.parseFloat(this.fields.get("restitution").getText());
        int radius = Integer.parseInt(this.fields.get("radius").getText());
        int mass = Integer.parseInt(this.fields.get("mass").getText());
        String color = this.fields.get("color").getText();

        return new Ball(
                position,
                velocity,
                acceleration,
                restitution,
                radius,
                mass,
                color
        );
    }

    private Vector2D getVector2DOf(String name) {
        double valX = Double.parseDouble(this.fields.get(name + "X").getText());
        double valY = Double.parseDouble(this.fields.get(name + "Y").getText());
        return new Vector2D(valX, valY);
    }
}
