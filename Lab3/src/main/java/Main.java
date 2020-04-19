import bear.Bear;
import animation.Animation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 600);

        Bear bear = new Bear();
        bear.draw(root);

        Animation animation = new animation.Animation();
        animation.applyFor(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
