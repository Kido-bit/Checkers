package checkers.logic.board;

import checkers.logic.game.Game;
import checkers.logic.game.GameStatusModule;
import checkers.logic.piece.Piece;
import checkers.logic.player.Player;
import lombok.*;

import java.awt.event.MouseEvent;

import static checkers.logic.board.SpotFactory.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    private final static int BOARD_SIDE_SIZE = 8;
    private Spot[][] boardSpots = new Spot[BOARD_SIDE_SIZE][BOARD_SIDE_SIZE];
    private Spot boardSpot;

    public Spot getBoardSpot(Point point) throws Exception {
        int x = point.getX();
        int y = point.getY();
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Space is out of bounds");
        }
        return boardSpots[x][y];
    }

    public Piece getStartPiece(GameStatusModule gameStatusModule) throws Exception {
        return gameStatusModule.getStartSpot().getPiece();
    }

    public Piece getPiece(GameStatusModule gameStatusModule) throws Exception {
        return gameStatusModule.getEndSpot().getPiece();
    }

    public void advancePiece(GameStatusModule gameStatusModule) throws Exception {
        int x = gameStatusModule.getEndSpot().getPoint().getX();
        int y = gameStatusModule.getEndSpot().getPoint().getY();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getEndSpot().getPoint();
        if(getPiece(gameStatusModule).isRegular()) {
            if (y == 7 && player.isWhite()) {
                boardSpots[x][y] = uberWhite(point);
            } else if (y == 0 && player.isBlack()) {
                boardSpots[x][y] = uberBlack(point);
            }
        }
    }

    public void setEndBoardSpot(int startX, int startY, int endX, int endY) {
        boardSpots[endX][endY].setPiece(boardSpots[startX][startY].getPiece());
    }

    public void setBoardPieceNull(int x, int y) {
        boardSpots[x][y].setPiece(null);
    }

    public void setSpotsAfterMove(GameStatusModule gameStatusModule) {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        int endX = gameStatusModule.getEndSpot().getPoint().getX();
        int endY = gameStatusModule.getEndSpot().getPoint().getX();
        setEndBoardSpot(startX, startY, endX, endY);
        setBoardPieceNull(startX, startY);
    }

    public void setBoardSpot(int x, int y, Spot spotFactory) {
        boardSpots[x][y] = spotFactory;
    }

    public void printBoard() {
        for (int y = BOARD_SIDE_SIZE - 1; y >= 0; y--) {
            System.out.println();
            System.out.print((y + 1) + " ");
            for (int x = 0; x < BOARD_SIDE_SIZE; x++) {
                System.out.print("|");
                if (boardSpots[x][y] == null || boardSpots[x][y].getPiece() == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(boardSpots[x][y].getPiece().getPieceIcon());
                }
            }
            System.out.print("|");
        }
        System.out.println("\n   a b c d e f g h");
    }

    public boolean pieceIsWhite(Point point) throws Exception {
        return hasPiece(point) && getBoardSpot(point).getPiece().isWhite();
    }

    public boolean pieceIsBlack(Point point) throws Exception {
        return hasPiece(point) && !pieceIsWhite(point);
    }

    public boolean hasPiece(Point point) throws Exception {
        return getBoardSpot(point).getPiece() != null;
    }

    public boolean hasNoPiece(Point point) throws Exception {
        return !hasPiece(point);
    }

    public boolean isEmpty(Point point) throws Exception {
        return getBoardSpot(point) == null;
    }

    public void resetBoard() {

        boardSpots[0][0] = regularWhite(new Point(0, 0));
        boardSpots[2][0] = regularWhite(new Point(2, 0));
        boardSpots[4][0] = regularWhite(new Point(4, 0));
        boardSpots[6][0] = regularWhite(new Point(6, 0));
        boardSpots[1][1] = regularWhite(new Point(1, 1));
        boardSpots[3][1] = regularWhite(new Point(3, 1));
        boardSpots[5][1] = regularWhite(new Point(5, 1));
        boardSpots[7][1] = regularWhite(new Point(7, 1));
        boardSpots[0][2] = regularWhite(new Point(0, 2));
        boardSpots[2][2] = regularWhite(new Point(2, 2));
        boardSpots[4][2] = regularWhite(new Point(4, 2));
        boardSpots[6][2] = regularWhite(new Point(6, 2));
//        boardSpots[6][0] = regularWhite(6, 0);
//        boardSpots[1][1] = regularWhite(1, 1);
//        boardSpots[3][1] = regularWhite(3, 1);
//        boardSpots[5][1] = regularWhite(5, 1);
//        boardSpots[7][1] = regularWhite(7, 1);
//        boardSpots[0][2] = regularWhite(0, 2);
//        boardSpots[2][2] = regularWhite(2, 2);
//        boardSpots[4][2] = regularWhite(4, 2);
//        boardSpots[6][2] = regularWhite(6, 2);

        // initialize black pieces
        boardSpots[1][5] = regularBlack(new Point(1, 5));
        boardSpots[3][5] = regularBlack(new Point(3, 5));
        boardSpots[5][5] = regularBlack(new Point(5, 5));
        boardSpots[7][5] = regularBlack(new Point(7, 5));
        boardSpots[0][6] = regularBlack(new Point(0, 5));
        boardSpots[2][6] = regularBlack(new Point(2, 5));
        boardSpots[4][6] = regularBlack(new Point(4, 5));
        boardSpots[6][6] = regularBlack(new Point(6, 5));
        boardSpots[1][7] = regularBlack(new Point(1, 5));
        boardSpots[3][7] = regularBlack(new Point(3, 5));
        boardSpots[5][7] = regularBlack(new Point(5, 5));
        boardSpots[7][7] = regularBlack(new Point(7, 5));
//        boardSpots[3][5] = regularBlack(3, 5);
//        boardSpots[5][5] = regularBlack(5, 5);
//        boardSpots[7][5] = regularBlack(7, 5);
//        boardSpots[0][6] = regularBlack(0, 6);
//        boardSpots[2][6] = regularBlack(2, 6);
//        boardSpots[4][6] = regularBlack(4, 6);
//        boardSpots[6][6] = regularBlack(6, 6);
//        boardSpots[1][7] = regularBlack(1, 7);
//        boardSpots[3][7] = regularBlack(3, 7);
//        boardSpots[5][7] = regularBlack(5, 7);
//        boardSpots[7][7] = regularBlack(7, 7);

        // initialize empty spots
        boardSpots[1][3] = emptySpot(new Point(1, 3));
        boardSpots[3][3] = emptySpot(new Point(3, 3));
        boardSpots[5][3] = emptySpot(new Point(5, 3));
        boardSpots[7][3] = emptySpot(new Point(7, 3));
        boardSpots[0][4] = emptySpot(new Point(0, 4));
        boardSpots[2][4] = emptySpot(new Point(2, 4));
        boardSpots[4][4] = emptySpot(new Point(4, 4));
        boardSpots[6][4] = emptySpot(new Point(6, 4));
//        boardSpots[3][3] = emptySpot(3, 3);
//        boardSpots[5][3] = emptySpot(5, 3);
//        boardSpots[7][3] = emptySpot(7, 3);
//        boardSpots[0][4] = emptySpot(0, 4);
//        boardSpots[2][4] = emptySpot(2, 4);
//        boardSpots[4][4] = emptySpot(4, 4);
//        boardSpots[6][4] = emptySpot(6, 4);

    }

}
