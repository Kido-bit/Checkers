package checkers.logic.board;

import checkers.logic.game.Game;
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

    private int x;
    private int y;
    private Piece piece;

    public static boolean validateStartSpot(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        if (board.isEmpty(game.startX, game.startY)) {
            System.out.println("Invalid board spot!");
        } else if (board.hasNoPiece(game.startX, game.startY)) {
            System.out.println("No checker here!");
        } else if ((player.isWhite() && board.pieceIsBlack(game.startX, game.startY)) || (player.isBlack()) && board.pieceIsWhite(game.startX, game.startY)) {
            System.out.println("Not your checker!");
        } else return board.getStartPiece(game).hasMove(game);
        return false;
    }

    public static boolean validateEndSpot(Board board, int endX, int endY) throws Exception {
        if (board.isEmpty(endX, endY)) {
            System.out.println("Invalid board spot!");
            return false;
        } else if (board.hasPiece(endX, endY)) {
            System.out.println("Checker on spot!");
            return false;
        }
        return true;
    }

    public static boolean checkPrimaryMove(Player player, int startX, int startY, int endX, int endY) {
        if ((player.isWhite() && (startY - endY) != -1) ||
                (player.isBlack() && (startY - endY != 1)) ||
                (startX - endX != -1 && startX - endX != 1)) {
        } else {
            return true;
        }
        return false;
    }

    public boolean isStartSpotValid(Game game) throws Exception {
        Board board = game.board;
        if (piece.isRegular()) {
            return piece.hasMove(game);
        } else {
            if (piece.hasMove(game)) {
                return true;
            } else return board.getStartPiece(game).hasKill(game);
        }
    }

    public static boolean isEndSpotValid(Game game) throws Exception {
        if (game.board.getStartPiece(game).isRegular()) {
            return checkPrimaryMove(game.currentPlayer, game.startX, game.startY, game.endX, game.endY);
        } else {
            return game.board.getStartPiece(game).possiblePrimaryMoves(game).contains(String.valueOf(game.endX) + game.endY);
        }
    }
}
