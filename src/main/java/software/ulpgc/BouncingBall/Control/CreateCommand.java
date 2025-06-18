package software.ulpgc.BouncingBall.Control;

import software.ulpgc.BouncingBall.Model.Ball;

public class CreateCommand implements Command {
    ContentPresenter presenter;

    public CreateCommand(ContentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(Object object) {
        if (object instanceof Ball) presenter.scheduleTask((Ball) object);
        else throw new RuntimeException();
    }
}
