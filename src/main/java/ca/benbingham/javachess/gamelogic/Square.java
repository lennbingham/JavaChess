package ca.benbingham.javachess.gamelogic;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square {
    /*
    Pretty much the most important class within the program and the only instance within the project of object orientated programming.

    There is an object created from this class to represent each square on the board and each object changes to represent the piece on the tile.
    information includes:
        - The available moves that piece has (calculated with a method from the "availableMoves" class)
        - The squares position in the 8x8 grid
        - Its GUI information
        - And more

     */
    public int row;
    public int column;
    public Button buttonObject;
    public String text;
    public String[] availableMoves = new String[64];
    public String pieceName = "";
    public String pieceColour = "";
    public int pos;
    public boolean hasMoved = false;

    public Square(int x, int y, Button buttonObj, String buttonText, int position){
        row = y;
        column = x;
        buttonObject = buttonObj;
        text = buttonText;
        pos = position;

        for (int i = 0; i < 64; i++) {
            availableMoves[i] = "";
        }
    }

    public Square() {

    }

    public void addPieceImageToButton(int numberInList, Square[] squareList) {
        Image blankPieceImage = new Image("Blank.png");
        ImageView blankPieceView = new ImageView(blankPieceImage);

        Image whitePawnImage = new Image("White-Pawn.png");
        ImageView whitePawnView = new ImageView(whitePawnImage);
        whitePawnView.translateYProperty().setValue(-5);
        Image blackPawnImage = new Image("Black-Pawn.png");
        ImageView blackPawnView = new ImageView(blackPawnImage);
        blackPawnView.translateYProperty().setValue(-5);

        Image whiteKingImage = new Image("White-King.png");
        ImageView whiteKingView = new ImageView(whiteKingImage);
        whiteKingView.translateYProperty().setValue(-4);
        Image blackKingImage = new Image("Black-King.png");
        ImageView blackKingView = new ImageView(blackKingImage);
        blackKingView.translateYProperty().setValue(-4);

        Image whiteRookImage = new Image("White-Rook.png");
        ImageView whiteRookView = new ImageView(whiteRookImage);
        whiteRookView.translateYProperty().setValue(-4);
        Image blackRookImage = new Image("Black-Rook.png");
        ImageView blackRookView = new ImageView(blackRookImage);
        blackRookView.translateYProperty().setValue(-4);

        Image whiteQueenImage = new Image("White-Queen.png");
        ImageView whiteQueenView = new ImageView(whiteQueenImage);
        whiteQueenView.translateYProperty().setValue(-4);
        Image blackQueenImage = new Image("Black-Queen.png");
        ImageView blackQueenView = new ImageView(blackQueenImage);
        blackQueenView.translateYProperty().setValue(-4);

        Image whiteBishopImage = new Image("White-Bishop.png");
        ImageView whiteBishopView = new ImageView(whiteBishopImage);
        whiteBishopView.translateYProperty().setValue(-4);
        Image blackBishopImage = new Image("Black-Bishop.png");
        ImageView blackBishopView = new ImageView(blackBishopImage);
        blackBishopView.translateYProperty().setValue(-4);

        Image whiteKnightImage = new Image("White-Knight.png");
        ImageView whiteKnightView = new ImageView(whiteKnightImage);
        whiteKnightView.translateYProperty().setValue(-4);
        Image blackKnightImage = new Image("Black-Knight.png");
        ImageView blackKnightView = new ImageView(blackKnightImage);
        blackKnightView.translateYProperty().setValue(-4);

        if (squareList[numberInList].pieceColour.equals("white")) {
            switch (squareList[numberInList].pieceName) {
                case "pawn":
                    squareList[numberInList].buttonObject.setGraphic(whitePawnView);
                    break;
                case "king":
                    squareList[numberInList].buttonObject.setGraphic(whiteKingView);
                    break;
                case "knight":
                    squareList[numberInList].buttonObject.setGraphic(whiteKnightView);
                    break;
                case "rook":
                    squareList[numberInList].buttonObject.setGraphic(whiteRookView);
                    break;
                case "queen":
                    squareList[numberInList].buttonObject.setGraphic(whiteQueenView);
                    break;
                case "bishop":
                    squareList[numberInList].buttonObject.setGraphic(whiteBishopView);
                    break;
                default:
                    squareList[numberInList].buttonObject.setGraphic(blankPieceView);
                    break;
            }
        }
        else {
            switch (squareList[numberInList].pieceName) {
                case "pawn":
                    squareList[numberInList].buttonObject.setGraphic(blackPawnView);
                    break;
                case "king":
                    squareList[numberInList].buttonObject.setGraphic(blackKingView);
                    break;
                case "knight":
                    squareList[numberInList].buttonObject.setGraphic(blackKnightView);
                    break;
                case "rook":
                    squareList[numberInList].buttonObject.setGraphic(blackRookView);
                    break;
                case "queen":
                    squareList[numberInList].buttonObject.setGraphic(blackQueenView);
                    break;
                case "bishop":
                    squareList[numberInList].buttonObject.setGraphic(blackBishopView);
                    break;
                default:
                    squareList[numberInList].buttonObject.setGraphic(blankPieceView);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "Position: " + this.pos + ", Piece Colour: " + this.pieceColour + ", Piece Name: " + this.pieceName;
    }

    public void copySquare(Square source, Square destination) {
        destination.pos = source.pos;
        destination.pieceName = source.pieceName;
        destination.pieceColour = source.pieceColour;
        System.arraycopy(source.availableMoves, 0, destination.availableMoves, 0, 64);
    }
}
