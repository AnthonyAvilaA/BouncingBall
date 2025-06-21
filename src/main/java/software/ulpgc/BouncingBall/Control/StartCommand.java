package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.Model.Ball;

import java.util.List;

public class StartCommand implements Command {
    ContentPresenter presenter;

    public StartCommand(ContentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Object object) {
        if (!(object instanceof List<?>)) {
            throw new IllegalArgumentException("Expected a List of Ball objects");
        }
        presenter.scheduleTask((List<Ball>) object);
    }
}
