package ca.benbingham.javachess.gamelogic;

import ca.benbingham.javachess.popupwindow.Window;
import ca.benbingham.javachess.popupwindow.CheckmateWindow;
import ca.benbingham.javachess.popupwindow.PawnPromotion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    /*
    This class handles most of the game code (ie method calls) and the overall flow of the actual game such as who goes first and detecting win states (via method calls)
    It also handles almost all the GUI needs for the game such as: CSS, Window initialization, Window Title, and button events.
     */
    public static void main(String[] args) {
        launch();
    }

    public Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        GridPane boardGrid = new GridPane();

        Scene scene = new Scene(boardGrid, 512, 512);

        Window mainWindow = new Window("Java Chess", 512, 512, boardGrid, stage, scene);

        mainWindow.stage.setResizable(false);
        mainWindow.stage.show();
        mainWindow.stage.setTitle(mainWindow.title);
        mainWindow.stage.setScene(scene);

        mainWindow.scene.getStylesheets().add("JavaChess.css");

        /*
        * This array holds every square on the board and is passed into almost all methods and objects. It is very important.
        * */
        Square[] squareList = new Square[64];

        int counter = 0;

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                squareList[counter] = new Square(i, j, null, String.valueOf(counter), counter);
                counter++;
            }
        }

        for (int i = 0; i < 64; i++) {
            squareList[i].buttonObject = new Button();
            squareList[i].buttonObject.setMinHeight(64);
            squareList[i].buttonObject.setMinWidth(64);
            squareList[i].buttonObject.getStyleClass().add("square-base");

            squareList[i].addPieceImageToButton(i, squareList);

            final int n = i;
            squareList[i].buttonObject.setOnAction(e -> pressButton(n, squareList));
            boardGrid.add(squareList[i].buttonObject, squareList[i].column, squareList[i].row);
        }

        // This block colours every square
        counter = 0;
        int evenOrOdd = 1;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((y % 2) == evenOrOdd) {
                    squareList[counter].buttonObject.getStyleClass().add("square-black");
                } else {
                    squareList[counter].buttonObject.getStyleClass().add("square-white");
                }
                counter++;
            }
            if (evenOrOdd == 0) {
                evenOrOdd = 1;
            }
            else {
                evenOrOdd = 0;
            }
        }

        /*
        A drawing that shows which number relates to each square on the board
        _________________________
        | 0| 1| 2| 3| 4| 5| 6| 7|
        |-----------------------|
        | 8| 9|10|11|12|13|14|15|
        |-----------------------|
        |16|17|18|19|20|21|22|23|
        |-----------------------|
        |24|25|26|27|28|29|30|31|
        |-----------------------|
        |32|33|34|35|36|37|38|39|
        |-----------------------|
        |40|41|42|43|44|45|46|47|
        |-----------------------|
        |48|49|50|51|52|53|54|55|
        |-----------------------|
        |56|57|58|59|60|61|62|63|
        -------------------------
         */

        for (int i = 8; i < 16; i++) {
            createPiece(i, squareList, "pawn", "black");
        }

        for (int i = 48; i < 56; i++) {
            createPiece(i, squareList, "pawn", "white");
        }

        createPiece(0, squareList, "rook", "black");
        createPiece(7, squareList, "rook", "black");

        createPiece(56, squareList, "rook", "white");
        createPiece(63, squareList, "rook", "white");

        createPiece(1, squareList, "knight", "black");
        createPiece(6, squareList, "knight", "black");

        createPiece(57, squareList, "knight", "white");
        createPiece(62, squareList, "knight", "white");

        createPiece(2, squareList, "bishop", "black");
        createPiece(5, squareList, "bishop", "black");

        createPiece(58, squareList, "bishop", "white");
        createPiece(61, squareList, "bishop", "white");

        createPiece(3, squareList, "queen", "black");
        createPiece(59, squareList, "queen", "white");



        createPiece(4, squareList, "king", "black");
        createPiece(60, squareList, "king", "white");
    }

    int playerTurn = 1;
    String playerTurnColour = "white";
    boolean gameOver = false;
    int lastClick = -1;

    public static void movePiece(int start, int end, Square[] squareList) {
        squareList[end].pieceName = squareList[start].pieceName;
        squareList[end].pieceColour = squareList[start].pieceColour;

        squareList[start].pieceColour = "";
        squareList[start].pieceName = "";
    }

    public void changeTheTurn() {
        if (playerTurn == 1) {
            playerTurn = 2;
            playerTurnColour = "black";
        }
        else {
            playerTurn = 1;
            playerTurnColour = "white";
        }
    }

    /*
    * This method is arguably the most important in the project due to how often it is called and what it does.
    * It is called whenever any button is pressed and based souly on which one is pressed and access to the "squareList" array
    * this method can decicde what should happen next, including:
    *   - Advancing the turn
    *   - Taking pieces
    *   - Moving pieces
    *   - Detecting Check
    *   - Detecting Checkmate
    *   - and ending the game
    * */
    public void pressButton(int numberInList, Square[] squareList) {
        AvailableMoves availableMoves = new AvailableMoves(squareList);

        if (!gameOver) {
            for (int i = 0; i < 64; i++) {
                squareList[i].buttonObject.getStyleClass().remove("square-selected");
                squareList[i].buttonObject.getStyleClass().remove("square-available");
                squareList[i].buttonObject.getStyleClass().remove("square-kill");
            }

            squareList[numberInList].buttonObject.getStyleClass().add("square-selected");

            if (squareList[numberInList].pieceColour.equals(playerTurnColour)) {
                availableMoves.clearAvailableMoves(numberInList);
                if (lastClick != -1) {
                    availableMoves.clearAvailableMoves(lastClick);
                }
                availableMoves.generateAvailableMoves(numberInList);
                availableMoves.testAvailableMovesForCheck(numberInList);

                for (int i = 0; i < 64; i++) {
                    if (squareList[numberInList].availableMoves[i].equals("A")) {
                        squareList[i].buttonObject.getStyleClass().add("square-available");
                    }
                    if (squareList[numberInList].availableMoves[i].equals("K")) {
                        squareList[i].buttonObject.getStyleClass().add("square-kill");
                    }
                }
            }
            if (lastClick != -1 && !squareList[lastClick].availableMoves[numberInList].equals("") ) {
                PawnPromotion promotion = new PawnPromotion(squareList);

                movePiece(lastClick, numberInList, squareList);

                squareList[numberInList].hasMoved = true;

                availableMoves.checkForCheck(true);
                if (availableMoves.blackKingCheck || availableMoves.whiteKingCheck) {
                    if (playerTurnColour.equals("white")) {
                        availableMoves.checkForCheckmate("black", true);
                    }
                    else if (playerTurnColour.equals("black")) {
                        availableMoves.checkForCheckmate("white", true);
                    }
                }

                if (availableMoves.blackKingCheckmate || availableMoves.whiteKingCheckmate) {
                    CheckmateWindow checkmateWindow = new CheckmateWindow(mainStage);
                    gameOver = true;
                    for (int i = 0; i < 64; i++) {
                        squareList[i].buttonObject.getStyleClass().remove("square-selected");
                        squareList[i].buttonObject.getStyleClass().remove("square-available");
                        squareList[i].buttonObject.getStyleClass().remove("square-kill");
                    }

                    checkmateWindow.showWindow();
                    if (availableMoves.blackKingCheckmate) {checkmateWindow.setWinner("white");}
                    if (availableMoves.whiteKingCheckmate) {checkmateWindow.setWinner("black");}

                }

                squareList[numberInList].addPieceImageToButton(numberInList, squareList);
                squareList[numberInList].addPieceImageToButton(lastClick, squareList);

                changeTheTurn();

                if (squareList[numberInList].row == 7 && squareList[numberInList].pieceColour.equals("black") && squareList[numberInList].pieceName.equals("pawn")) {
                    promotion.showPawnPromotion("black", numberInList);
                    return;
                }
                if (squareList[numberInList].row == 0 && squareList[numberInList].pieceColour.equals("white") && squareList[numberInList].pieceName.equals("pawn")) {
                    promotion.showPawnPromotion("white", numberInList);
                    return;
                }
            }
            if (!squareList[numberInList].pieceName.equals("") && squareList[numberInList].pieceColour.equals(playerTurnColour)) {
                lastClick = numberInList;
            }
            else {
                lastClick = -1;
            }
        }
        else {
            for (int i = 0; i < 64; i++) {
                squareList[i].buttonObject.setDisable(true);
            }
        }
    }

    public static void createPiece(int numberInList, Square[] squareList, String name, String colour) {
        squareList[numberInList].pieceColour = colour;
        squareList[numberInList].pieceName = name;
        squareList[numberInList].addPieceImageToButton(numberInList, squareList);
    }
}
