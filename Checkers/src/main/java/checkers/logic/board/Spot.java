package checkers.logic.board;

import checkers.logic.game.GameStatusModule;
import checkers.logic.piece.Piece;
import checkers.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Spot {

    private Point point;
    private Piece piece;

    public boolean hasPiece(Board board, Point point) throws Exception {
        return board.getBoardSpot(point).getPiece() != null;
    }

    public boolean hasNoPiece(Board board, Point point) throws Exception {
        return !hasPiece(board, point);
    }

    public static boolean validateStartSpot(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (board.isEmpty(point)) {
            System.out.println("Invalid board spot!");
        } else if (board.hasNoPiece(point)) {
            System.out.println("No checker here!");
        } else if ((player.isWhite() && board.pieceIsBlack(point)) || (player.isBlack()) && board.pieceIsWhite(point)) {
            System.out.println("Not your checker!");
        } else return board.getStartPiece(gameStatusModule).hasMove(gameStatusModule);
        return false;
    }

    public static boolean validateEndSpot(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getEndSpot().getPoint();
        if (board.isEmpty(point)) {
            System.out.println("Invalid board spot!");
            return false;
        } else if (board.hasPiece(point)) {
            System.out.println("Checker on spot!");
            return false;
        }
        return true;
    }

    public static boolean checkPrimaryMove(GameStatusModule gameStatusModule) {
        Player player = gameStatusModule.getPlayer();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        int endX = gameStatusModule.getEndSpot().getPoint().getX();
        int endY = gameStatusModule.getEndSpot().getPoint().getY();
        if ((player.isWhite() && (startY - endY) != -1) ||
                (player.isBlack() && (startY - endY != 1)) ||
                (startX - endX != -1 && startX - endX != 1)) {
        } else {
            return true;
        }
        return false;
    }

    public boolean isStartSpotValid(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        if (piece.isRegular()) {
            return piece.hasMove(gameStatusModule);
        } else {
            if (piece.hasMove(gameStatusModule)) {
                return true;
            } else return board.getStartPiece(gameStatusModule).hasKill(gameStatusModule);
        }
    }

    public static boolean isEndSpotValid(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        int endX = gameStatusModule.getEndSpot().getPoint().getX();
        int endY = gameStatusModule.getEndSpot().getPoint().getY();
        if (board.getStartPiece(gameStatusModule).isRegular()) {
            return checkPrimaryMove(gameStatusModule);
        } else {
            return board.getStartPiece(gameStatusModule).possiblePrimaryMoves(gameStatusModule).contains(String.valueOf(endX) + endY);
        }
    }

    public void setX(int parseInt) {

    }
}
