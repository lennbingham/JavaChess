package ca.benbingham.javachess;

import ca.benbingham.javachess.gamelogic.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();

        Label label = new Label("Test");

        Image image = new Image("mcpickle.jpg");

        ImageView imageView = new ImageView(image);

        Scene scene = new Scene(gridPane, 500, 500);

        stage.setScene(scene);

        gridPane.add(label, 0, 0);
        gridPane.add(imageView, 0, 1);

        stage.show();
    }

    public static void main(String[] args) {
        Main.main(args);
    }
}
