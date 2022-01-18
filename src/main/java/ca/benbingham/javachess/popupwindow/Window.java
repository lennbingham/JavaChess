package ca.benbingham.javachess.popupwindow;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*
* This class allows for easier creation and access of window information for all windows in the project including the pawn promotion popup
* */
public class Window {
    public String title;
    public int width;
    public int height;
    public GridPane grid;
    public Stage stage;
    public Scene scene;
    public Pane pane;

    public Window(String windowTitle, int windowWidth, int windowHeight, GridPane windowGridPane, Stage windowStage, Scene windowScene) {
        title = windowTitle;
        width = windowWidth;
        height = windowHeight;
        grid = windowGridPane;
        stage = windowStage;
        scene = windowScene;
    }
    public Window(String windowTitle, int windowWidth, int windowHeight, Pane windowPane, Stage windowStage, Scene windowScene) {
        title = windowTitle;
        width = windowWidth;
        height = windowHeight;
        pane = windowPane;
        stage = windowStage;
        scene = windowScene;
    }
}
