import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
*   Elements:
*       BG - 127, 254, 4
*       Box - 255, 163, 10
*       Screen - 128, 128, 128
*       Buttons&Lines - 9, 0, 16
*
**/

public class Main extends Application {
    private int scale = 30;

    private int Scale(double base) {
        return (int)(base * scale);
    }


    public static void main(String[] args) {
        launch(args);
    }

    private Scene getScene(Group root) {
        int width = Scale(21);
        int height = Scale(18);

        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.rgb(127, 254, 4));

        return scene;
    }

    private Rectangle getBox() {
        int width = Scale(15);
        int height = Scale(10);
        int positionX = Scale(3);
        int positionY = Scale(5);
        Color color = Color.rgb(255, 163, 10);

        Rectangle box = new Rectangle(positionX, positionY, width, height);
        box.setFill(color);
        return box;
    }

    private Rectangle getScreen() {
        int width = Scale(10);
        int height = Scale(8);
        int positionX = Scale(4);
        int positionY = Scale(6);
        Color color = Color.rgb(128, 128, 128);

        Rectangle screen = new Rectangle(positionX, positionY, width, height);
        screen.setFill(color);

        screen.setArcWidth(20);
        screen.setArcHeight(20);

        return screen;
    }

    private List<Circle> getButtonSet() {
        int positionX = Scale(16.5);
        int radius = Scale(0.5);
        Color color = Color.rgb(9, 0, 16);

        List<Integer> positionsY = new ArrayList<>(Arrays.asList(Scale(9.5), Scale(11.5), Scale(13.5)));
        List<Circle> set = new ArrayList<>();
        positionsY.forEach((Integer positionY) ->
                set.add(new Circle(positionX, positionY, radius)));
        set.forEach((Circle circle) -> circle.setFill(color));
        return set;
    }

    private Polyline getAntenna() {
        Double[] points = new Double[]{
            7.0, 1.0,
            11.0, 5.0,
            15.0, 1.0,
        };
        Polyline antenna = new Polyline();
        for(int i = 0; i < points.length; i++)
            points[i] = Scale(points[i]) * 1.0;
        antenna.getPoints().addAll(points);
        return antenna;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = getScene(root);

        root.getChildren().add(getBox());
        root.getChildren().add(getScreen());
        root.getChildren().addAll(getButtonSet());
        root.getChildren().add(getAntenna());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
