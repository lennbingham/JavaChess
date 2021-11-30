package ca.benbingham.javachess.popupwindow;

import ca.benbingham.javachess.gamelogic.Square;
import ca.benbingham.javachess.gamelogic.Main;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PawnPromotion {
    Stage promotionStage = new Stage();
    GridPane promotionGrid = new GridPane();
    Scene promotionScene = new Scene(promotionGrid, 128, 128);

    Window pawnPromotion = new Window("Promotion", 128, 128, promotionGrid, promotionStage, promotionScene);

    Square[] promotionSquareList = new Square[4];
    public Square[] squareListCopy;

    int pawnClicked;

    public PawnPromotion(Square[] squareList) {
        this.pawnPromotion.stage.setTitle(this.pawnPromotion.title);
        this.pawnPromotion.stage.setScene(this.pawnPromotion.scene);
        this.pawnPromotion.stage.setResizable(false);
        this.pawnPromotion.stage.initModality(Modality.APPLICATION_MODAL);

        this.pawnPromotion.scene.getStylesheets().add("JavaChess.css");

        squareListCopy = squareList;

        int counter = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                promotionSquareList[counter] = new Square(i, j, null, String.valueOf(counter), counter);
                counter++;
            }
        }

        for (int i = 0; i < 4; i++) {
            promotionSquareList[i].buttonObject = new Button();
            promotionSquareList[i].buttonObject.setMinHeight(64);
            promotionSquareList[i].buttonObject.setMinWidth(64);
            promotionSquareList[i].buttonObject.getStyleClass().add("square-base");

            promotionGrid.add(promotionSquareList[i].buttonObject, promotionSquareList[i].column, promotionSquareList[i].row);

            final int n = i;
            promotionSquareList[i].buttonObject.setOnAction(e -> promotionButtonPress(n));
        }

        promotionSquareList[0].buttonObject.getStyleClass().add("square-white");
        promotionSquareList[1].buttonObject.getStyleClass().add("square-black");
        promotionSquareList[2].buttonObject.getStyleClass().add("square-black");
        promotionSquareList[3].buttonObject.getStyleClass().add("square-white");

        this.pawnPromotion.stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, Event::consume);
    }

    public void showPawnPromotion(String colour, int numberInList) {
        this.pawnPromotion.stage.show();
        this.displayButtons(colour);
        pawnClicked = numberInList;
    }

    public void displayButtons(String colour) {
        promotionSquareList[0].pieceName = "bishop";
        promotionSquareList[1].pieceName = "knight";
        promotionSquareList[2].pieceName = "queen";
        promotionSquareList[3].pieceName = "rook";

        if (colour.equals("white")) {
            for (int i = 0; i < 4; i++) {
                promotionSquareList[i].pieceColour = "white";
                promotionSquareList[i].addPieceImageToButton(i, promotionSquareList);
            }
        }
        else {
            for (int i = 0; i < 4; i++) {
                promotionSquareList[i].pieceColour = "black";
                promotionSquareList[i].addPieceImageToButton(i, promotionSquareList);
            }
        }
    }

    public void promotionButtonPress(int numberInList) {
        promotionSquareList[numberInList].buttonObject.getStyleClass().add("square-selected");

        switch (promotionSquareList[numberInList].pieceName) {
            case "queen":
                Main.createPiece(pawnClicked, squareListCopy, "queen", promotionSquareList[numberInList].pieceColour);
                break;
            case "bishop":
                Main.createPiece(pawnClicked, squareListCopy, "bishop", promotionSquareList[numberInList].pieceColour);
                break;
            case "rook":
                Main.createPiece(pawnClicked, squareListCopy, "rook", promotionSquareList[numberInList].pieceColour);
                break;
            case "knight":
                Main.createPiece(pawnClicked, squareListCopy, "knight", promotionSquareList[numberInList].pieceColour);
                break;
        }
        pawnPromotion.stage.hide();
    }
}
