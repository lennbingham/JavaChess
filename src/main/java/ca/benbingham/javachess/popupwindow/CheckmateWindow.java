package ca.benbingham.javachess.popupwindow;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CheckmateWindow {

    Stage checkmateStage = new Stage();
    StackPane checkmatePane = new StackPane();
    Scene checkmateScene = new Scene(checkmatePane, 300, 75);

    Window checkmateWindow = new Window("Checkmate", 128, 128, checkmatePane, checkmateStage, checkmateScene);

    String winner;

    Text checkmateLabel = new Text();

    public CheckmateWindow(Stage mainStage) {
        this.checkmateWindow.stage.setTitle(this.checkmateWindow.title);
        this.checkmateWindow.stage.setScene(this.checkmateWindow.scene);
        this.checkmateWindow.stage.setResizable(false);
        this.checkmateWindow.stage.initModality(Modality.APPLICATION_MODAL);

        checkmateWindow.pane.getChildren().add(checkmateLabel);
        checkmateLabel.setTextAlignment(TextAlignment.CENTER);

        this.checkmateWindow.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> mainStage.fireEvent(new WindowEvent(mainStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
    }

    public void setWinner(String winner) {
        this.winner = winner;
        if (winner.equals("white")) {
            checkmateLabel.setText("The black king has been checkmated. White wins!");
        }
        else if (winner.equals("black")) {
            checkmateLabel.setText("The white king has been checkmated. Black wins!");
        }
    }

    public void showWindow() {
        this.checkmateWindow.stage.show();
    }
}
