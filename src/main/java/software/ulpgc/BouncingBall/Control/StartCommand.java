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
        presenter.scheduleTask((List<Ball>) object);
    }
}
