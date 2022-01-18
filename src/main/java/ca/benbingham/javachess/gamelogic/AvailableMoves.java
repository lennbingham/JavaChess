package ca.benbingham.javachess.gamelogic;

public class AvailableMoves {
    /*
    This class is dedicated to calculating the available moves for a given piece. Each piece is given its own method that is than called when needed.
     */
    Square[] squareList;
    public boolean blackKingCheck;
    public boolean whiteKingCheck;

    public boolean blackKingCheckmate;
    public boolean whiteKingCheckmate;

    // The class constructor which gets the "squareList" variable allowing all non-static methods within the class access.
    public AvailableMoves(Square[] squareList) {
        this.squareList = squareList;
    }


    public void clearAvailableMoves(int numberInList) {
        for (int i = 0; i < 64; i++) {
            squareList[numberInList].availableMoves[i] = "";
        }
    }

    public Square getInfoOnSquare(int row, int column) {
        if ((row * 8) + column < 0 || (row * 8) + column > 63) {
            return null;
        } else {
            return squareList[(row * 8) + column];
        }
    }

    public void setAvailableMoves(int numberInList, int valueToChange, String value) {
        squareList[numberInList].availableMoves[valueToChange] = value;
    }

    public void checkAndAssignAvailability(int numberInList, Square spot) {
        try {
            if (spot.pos > -1 && spot.pos < 64) {
                if (spot.pieceName.equals("")) {
                    setAvailableMoves(numberInList, getInfoOnSquare(spot.row, spot.column).pos, "A");
                } else if (!spot.pieceColour.equals(squareList[numberInList].pieceColour)) {
                    setAvailableMoves(numberInList, spot.pos, "K");
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void checkAndAssignAvailabilityNoKills(int numberInList, Square spot) {
        try {
            if (spot.pos > 0 && spot.pos < 64) {
                if (spot.pieceName.equals("")) {
                    setAvailableMoves(numberInList, getInfoOnSquare(spot.row, spot.column).pos, "A");
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void checkAndAssignAvailabilityOnlyKills(int numberInList, Square spot) {
        try {
            if (spot.pos > 0 && spot.pos < 64) {
                if (!spot.pieceColour.equals(squareList[numberInList].pieceColour) && !spot.pieceName.equals("")) {
                    setAvailableMoves(numberInList, spot.pos, "K");
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void pawn(int numberInList) {
        clearAvailableMoves(numberInList);

        Square oneSpotInFront;
        Square twoSpotsInFront;
        Square diagonalRightSpot;
        Square diagonalLeftSpot;

        int numberInListRow = squareList[numberInList].row;
        int numberInListColumn = squareList[numberInList].column;

        if (squareList[numberInList].pieceColour.equals("white")) {
            oneSpotInFront = getInfoOnSquare(numberInListRow - 1, numberInListColumn);
            twoSpotsInFront = getInfoOnSquare(numberInListRow - 2, numberInListColumn);

            diagonalRightSpot = getInfoOnSquare(numberInListRow - 1, numberInListColumn + 1);
            diagonalLeftSpot = getInfoOnSquare(numberInListRow - 1, numberInListColumn - 1);
        } else {
            oneSpotInFront = getInfoOnSquare(numberInListRow + 1, numberInListColumn);
            twoSpotsInFront = getInfoOnSquare(numberInListRow + 2, numberInListColumn);

            diagonalRightSpot = getInfoOnSquare(numberInListRow + 1, numberInListColumn - 1);
            diagonalLeftSpot = getInfoOnSquare(numberInListRow + 1, numberInListColumn + 1);
        }

        if (squareList[numberInList].row == 1 && squareList[numberInList].pieceColour.equals("black") && oneSpotInFront.pieceName.equals("")) {
            checkAndAssignAvailabilityNoKills(numberInList, twoSpotsInFront);
        }
        if (squareList[numberInList].row == 6 && squareList[numberInList].pieceColour.equals("white") && oneSpotInFront.pieceName.equals("")) {
            checkAndAssignAvailabilityNoKills(numberInList, twoSpotsInFront);
        }

        try {
            if (squareList[numberInList].pieceColour.equals("white")) {
                if (numberInListColumn == 7) {
                    if (diagonalLeftSpot.column < 8 && diagonalLeftSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalLeftSpot);
                    }
                }
                if (numberInListColumn == 0) {
                    if (diagonalRightSpot.column < 8 && diagonalRightSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalRightSpot);
                    }
                }
                if (numberInListColumn != 0 && numberInListColumn != 7) {
                    if (diagonalLeftSpot.column < 8 && diagonalLeftSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalLeftSpot);
                    }
                    if (diagonalRightSpot.column < 8 && diagonalRightSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalRightSpot);
                    }
                }
            }

            if (squareList[numberInList].pieceColour.equals("black")) {
                if (numberInListColumn == 7) {
                    if (diagonalRightSpot.column < 8 && diagonalRightSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalRightSpot);
                    }
                }
                if (numberInListColumn == 0) {
                    if (diagonalLeftSpot.column < 8 && diagonalLeftSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalLeftSpot);
                    }
                }
                if (numberInListColumn != 0 && numberInListColumn != 7) {
                    if (diagonalLeftSpot.column < 8 && diagonalLeftSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalLeftSpot);
                    }
                    if (diagonalRightSpot.column < 8 && diagonalRightSpot.row < 8) {
                        checkAndAssignAvailabilityOnlyKills(numberInList, diagonalRightSpot);
                    }
                }
            }

        } catch (Exception ignored) {
        }
        checkAndAssignAvailabilityNoKills(numberInList, oneSpotInFront);
    }

    public void king(int numberInList) {
        clearAvailableMoves(numberInList);

        int numberInListRow = squareList[numberInList].row;
        int numberInListColumn = squareList[numberInList].column;

        Square spotFront = getInfoOnSquare(numberInListRow - 1, numberInListColumn);
        Square spotBehind = getInfoOnSquare(numberInListRow + 1, numberInListColumn);
        Square spotLeft = getInfoOnSquare(numberInListRow, numberInListColumn - 1);
        Square spotRight = getInfoOnSquare(numberInListRow, numberInListColumn + 1);
        Square spotFrontRight = getInfoOnSquare(numberInListRow - 1, numberInListColumn + 1);
        Square spotFrontLeft = getInfoOnSquare(numberInListRow - 1, numberInListColumn - 1);
        Square spotBackRight = getInfoOnSquare(numberInListRow + 1, numberInListColumn + 1);
        Square spotBackLeft = getInfoOnSquare(numberInListRow + 1, numberInListColumn - 1);

        checkAndAssignAvailability(numberInList, spotFront);
        checkAndAssignAvailability(numberInList, spotBehind);
        if (squareList[numberInList].column != 0) {
            checkAndAssignAvailability(numberInList, spotLeft);
            checkAndAssignAvailability(numberInList, spotFrontLeft);
            checkAndAssignAvailability(numberInList, spotBackLeft);
        }
        if (squareList[numberInList].column != 7) {
            checkAndAssignAvailability(numberInList, spotRight);
            checkAndAssignAvailability(numberInList, spotFrontRight);
            checkAndAssignAvailability(numberInList, spotBackRight);
        }
    }

    public void rook(int numberInList, boolean clear) {
        if (clear) {
            clearAvailableMoves(numberInList);
        }

        int numberInListRow = squareList[numberInList].row;
        int numberInListColumn = squareList[numberInList].column;

        int spot;

        for (int i = 1; i < 9; i++) {
            spot = numberInListRow - i;
            if (spot <= -1) {
                break;
            }
            if (!getInfoOnSquare(spot, numberInListColumn).pieceName.equals("") && getInfoOnSquare(spot, numberInListColumn).pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, getInfoOnSquare(spot, numberInListColumn));
            if (squareList[numberInList].availableMoves[getInfoOnSquare(spot, numberInListColumn).pos].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < 9; i++) {
            spot = numberInListRow + i;
            if (spot >= 8) {
                break;
            }
            if (!getInfoOnSquare(spot, numberInListColumn).pieceName.equals("") && getInfoOnSquare(spot, numberInListColumn).pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, getInfoOnSquare(spot, numberInListColumn));
            if (squareList[numberInList].availableMoves[getInfoOnSquare(spot, numberInListColumn).pos].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < 9; i++) {
            spot = numberInListColumn - i;
            if (spot <= -1) {
                break;
            }
            if (!getInfoOnSquare(numberInListRow, spot).pieceName.equals("") && getInfoOnSquare(numberInListRow, spot).pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, getInfoOnSquare(numberInListRow, spot));
            if (squareList[numberInList].availableMoves[getInfoOnSquare(numberInListRow, spot).pos].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < 9; i++) {
            spot = numberInListColumn + i;
            if (spot >= 8) {
                break;
            }
            if (!getInfoOnSquare(numberInListRow, spot).pieceName.equals("") && getInfoOnSquare(numberInListRow, spot).pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, getInfoOnSquare(numberInListRow, spot));
            if (squareList[numberInList].availableMoves[getInfoOnSquare(numberInListRow, spot).pos].equals("K")) {
                break;
            }
        }
    }

    public void bishop(int numberInList, boolean clear) {
        if (clear) {
            clearAvailableMoves(numberInList);
        }

        int numberInListRow = squareList[numberInList].row;
        int numberInListColumn = squareList[numberInList].column;

        int spot;

        for (int i = 1; i < squareList[numberInList].column + 1; i++) {
            if (getInfoOnSquare(numberInListRow - i, numberInListColumn - i) == null) {
                break;
            }
            spot = getInfoOnSquare(numberInListRow - i, numberInListColumn - i).pos;
            if (spot <= -1) {
                break;
            }
            if (!squareList[spot].pieceName.equals("") && squareList[spot].pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, squareList[spot]);
            if (squareList[numberInList].availableMoves[spot].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < squareList[numberInList].column + 1; i++) {
            if (getInfoOnSquare(numberInListRow + i, numberInListColumn - i) == null) {
                break;
            }
            spot = getInfoOnSquare(numberInListRow + i, numberInListColumn - i).pos;
            if (spot <= -1 || spot >= 64) {
                break;
            }
            if (!squareList[spot].pieceName.equals("") && squareList[spot].pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, squareList[spot]);
            if (squareList[numberInList].availableMoves[spot].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < 8 - squareList[numberInList].column; i++) {
            if (getInfoOnSquare(numberInListRow + i, numberInListColumn + i) == null) {
                break;
            }
            spot = getInfoOnSquare(numberInListRow + i, numberInListColumn + i).pos;
            if (spot <= -1 || spot >= 64) {
                break;
            }
            if (!squareList[spot].pieceName.equals("") && squareList[spot].pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, squareList[spot]);
            if (squareList[numberInList].availableMoves[spot].equals("K")) {
                break;
            }
        }

        for (int i = 1; i < 8 - squareList[numberInList].column; i++) {
            if (getInfoOnSquare(numberInListRow - i, numberInListColumn + i) == null) {
                break;
            }
            spot = getInfoOnSquare(numberInListRow - i, numberInListColumn + i).pos;
            if (spot <= -1 || spot >= 64) {
                break;
            }
            if (!squareList[spot].pieceName.equals("") && squareList[spot].pieceColour.equals(squareList[numberInList].pieceColour)) {
                break;
            }
            checkAndAssignAvailability(numberInList, squareList[spot]);
            if (squareList[numberInList].availableMoves[spot].equals("K")) {
                break;
            }
        }
    }

    public void knight(int numberInList) {
        clearAvailableMoves(numberInList);

        int numberInListRow = squareList[numberInList].row;
        int numberInListColumn = squareList[numberInList].column;

        Square leftTopSpot = null;
        Square leftBottomSpot = null;
        Square rightTopSpot = null;
        Square rightBottomSpot = null;
        Square topLeftSpot = null;
        Square topRightSpot = null;
        Square bottomLeftSpot = null;
        Square bottomRightSpot = null;

        if (squareList[numberInList].column != 0 && squareList[numberInList].column != 1) {
            leftTopSpot = getInfoOnSquare(numberInListRow - 1, numberInListColumn - 2);
            leftBottomSpot = getInfoOnSquare(numberInListRow + 1, numberInListColumn - 2);
        }

        if (squareList[numberInList].column != 6 && squareList[numberInList].column != 7) {
            rightTopSpot = getInfoOnSquare(numberInListRow + 1, numberInListColumn + 2);
            rightBottomSpot = getInfoOnSquare(numberInListRow - 1, numberInListColumn + 2);
        }

        if (squareList[numberInList].column != 0) {
            topLeftSpot = getInfoOnSquare(numberInListRow - 2, numberInListColumn - 1);
            bottomLeftSpot = getInfoOnSquare(numberInListRow + 2, numberInListColumn - 1);
        }

        if (squareList[numberInList].column != 7) {
            topRightSpot = getInfoOnSquare(numberInListRow - 2, numberInListColumn + 1);
            bottomRightSpot = getInfoOnSquare(numberInListRow + 2, numberInListColumn + 1);
        }

        checkAndAssignAvailability(numberInList, leftTopSpot);
        checkAndAssignAvailability(numberInList, leftBottomSpot);
        checkAndAssignAvailability(numberInList, rightTopSpot);
        checkAndAssignAvailability(numberInList, rightBottomSpot);
        checkAndAssignAvailability(numberInList, topLeftSpot);
        checkAndAssignAvailability(numberInList, bottomLeftSpot);
        checkAndAssignAvailability(numberInList, topRightSpot);
        checkAndAssignAvailability(numberInList, bottomRightSpot);
    }

    public void queen(int numberInList) {
        clearAvailableMoves(numberInList);

        bishop(numberInList, false);
        rook(numberInList, false);
    }

    public void generateAvailableMoves(int numberInList) {
        AvailableMoves availableMoves = new AvailableMoves(squareList);
        switch (squareList[numberInList].pieceName) {
            case "pawn":
                availableMoves.pawn(numberInList);
                break;
            case "king":
                availableMoves.king(numberInList);
                break;
            case "bishop":
                availableMoves.bishop(numberInList, true);
                break;
            case "queen":
                availableMoves.queen(numberInList);
                break;
            case "rook":
                availableMoves.rook(numberInList, true);
                break;
            case "knight":
                availableMoves.knight(numberInList);
                break;
        }
    }

    public void checkForCheck(boolean print) {
        for (int i = 0; i < 64; i++) {
            generateAvailableMoves(i);
            for (int j = 0; j < 64; j++) {
                if (squareList[i].availableMoves[j].equals("K") && squareList[j].pieceName.equals("king")) {
                    if (squareList[i].pieceColour.equals("white")) {
                        blackKingCheck = true;
                        if (print) {
                            System.out.println("The black king is in check!");
                        }
                    }
                    if (squareList[i].pieceColour.equals("black")) {
                        whiteKingCheck = true;
                        if (print) {
                            System.out.println("The white king is in check!");
                        }
                    }
                }
            }
            clearAvailableMoves(i);
        }
        if (blackKingCheck || whiteKingCheck) {return;}
        blackKingCheck = false;
        whiteKingCheck = false;

    }

    public void testAvailableMovesForCheck(int numberInList) {
        String[] tempAvailableMoves = new String[64];

        Square tempPiece = new Square();
        boolean kill = false;

        System.arraycopy(squareList[numberInList].availableMoves, 0, tempAvailableMoves, 0, 64);

        for (int i = 0; i < 64; i++) {

            if (!tempAvailableMoves[i].equals("")) {
                if (tempAvailableMoves[i].equals("K")) {
                    tempPiece.copySquare(squareList[i], tempPiece);
                    kill = true;
                }

                Main.movePiece(squareList[numberInList].pos, i, squareList);

                checkForCheck(false);

                if (whiteKingCheck && squareList[i].pieceColour.equals("white")) {
                    //the move is illegal
                    tempAvailableMoves[i] = "";
                }else if (blackKingCheck && squareList[i].pieceColour.equals("black")) {
                    //the move is illegal
                    tempAvailableMoves[i] = "";
                }
                if (whiteKingCheck && blackKingCheck) {
                    //the move is illegal
                    tempAvailableMoves[i] = "";
                }

                Main.movePiece(i, squareList[numberInList].pos, squareList);

                whiteKingCheck = false;
                blackKingCheck = false;

                if (kill) {
                    tempPiece.copySquare(tempPiece, squareList[i]);
                    kill = false;
                }
            }
        }
        System.arraycopy(tempAvailableMoves, 0, squareList[numberInList].availableMoves, 0, 64);
    }

    public void checkForCheckmate(String colour, boolean print) {
        if (whiteKingCheck || blackKingCheck) {
            for (int i = 0; i < 64; i++) {
                if (squareList[i].pieceColour.equals(colour)) {
                    generateAvailableMoves(i);
                    testAvailableMovesForCheck(i);

                    for (int j = 0; j < 64; j++) {
                        if (!squareList[i].availableMoves[j].equals("")) {
                            if (squareList[i].pieceColour.equals("white")) {
                                whiteKingCheckmate = false;
                                return;
                            }
                            else if (squareList[i].pieceColour.equals("black")) {
                                blackKingCheckmate = false;
                                return;
                            }
                        }
                    }
                }
            }

            if (colour.equals("white")) {
                whiteKingCheckmate = true;
                if (print) {System.out.println("The white king is in checkmate!");}
            }
            else if (colour.equals("black")) {
                blackKingCheckmate = true;
                if (print) {System.out.println("The black king is in checkmate!");}
            }
        }
        else {
            whiteKingCheckmate = false;
            blackKingCheckmate = false;
        }

    }
}
