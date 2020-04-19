package animation;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Animation {
    public void applyFor(Group group) {
        this.runParallelAnimations(group);
    }

    private Transition move(Group group) {
        Path movement = new Path(
                new MoveTo(100, 100),
                new CubicCurveTo(300, 30, 500, 400, 900, 500)
        );

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setPath(movement);
        pathTransition.setNode(group);
        pathTransition.setAutoReverse(true);
        return pathTransition;
    }

    private Transition rotate(Group group) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2500), group);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(3);
        return rotateTransition;
    }

    private Transition scale(Group group) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(5000), group);
        scaleTransition.setToX(0.4);
        scaleTransition.setToY(0.4);
        scaleTransition.setAutoReverse(true);
        return scaleTransition;
    }

    private void runParallelAnimations(Group group) {
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                move(group),
                rotate(group),
                scale(group)
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.setAutoReverse(true);
        parallelTransition.play();
    }
}
