package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.game.Game;
import checkers.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Piece {

    public boolean white;
    private String pieceIcon;

    public String getPieceIcon() {
        return this.pieceIcon;
    }

    public Piece(boolean white) {
        this.white = white;
    }

    public abstract boolean isRegular();

    public abstract boolean hasMove(Game game) throws Exception;

    public boolean hasKill(Game game) throws Exception {
        return false;
    }

    public void advancePiece(Game game) throws Exception {
    }

    public boolean upLeftMove(Board board, int startX, int startY) throws Exception {
        return (board.hasNoPiece(startX - 1, startY + 1));
    }

    public boolean upRightMove(Board board, int startX, int startY) throws Exception {
        return (board.hasNoPiece(startX + 1, startY + 1));
    }

    public boolean downLeftMove(Board board, int startX, int startY) throws Exception {
        return (board.hasNoPiece(startX - 1, startY - 1));
    }

    public boolean downRightMove(Board board, int startX, int startY) throws Exception {
        return (board.hasNoPiece(startX + 1, startY - 1));
    }

    public boolean validateExceptionsMoves(Board board, int startX, int startY) {
        return false;
    }

    public boolean validateCenterMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        if (board.hasPiece(startX + 1, startY + 1) && board.hasPiece(startX - 1, startY + 1)) {
            if (hasKill(game)) {
                return true;
            } else {
                System.out.println("No move available!");
            }
        }
            return false;
    }

    public boolean validateLeftMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        if (startX == 0 && board.hasNoPiece(startX + 1, startY + 1)) {
            return true;
        }
        if (startX == 0 && board.hasPiece(startX + 1, startY + 1)) {
            if (hasKill(game)) {
                return true;
            } else {
                System.out.println("No move available!");
            }
        }
        return false;
    }

    public boolean validateRightMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        if (startX == 7 && (board.hasNoPiece(startX - 1, startY + 1))) {
            return true;
        }
        if (startX == 7 && board.hasPiece(startX - 1, startY + 1)) {
            if (hasKill(game)) {
                return true;
            } else {
                System.out.println("No move available!");
            }
        }
        return false;
    }

    public boolean validateTopMoves(Board board, int startX, int startY) {
        return false;
    }

    public boolean validateBottomMoves(Game game) throws Exception {
        return false;
    }

    public boolean upLeftKill(Game game) throws Exception {
        return false;
    }

    public boolean upRightKill(Game game) throws Exception {
        return false;
    }

    public boolean downLeftKill(Game game) throws Exception {
        return false;
    }

    public boolean downRightKill(Game game) throws Exception {
        return false;
    }

    public boolean validateExceptionsKilling(Game game) throws Exception {
        return false;
    }

    public boolean validateCenterKilling(Game game) throws Exception {
        return false;
    }

    public boolean validateLeftSideKilling(Game game) throws Exception {
        return false;
    }

    public boolean validateRightSideKilling(Game game) throws Exception {
        return false;
    }

    public boolean validateTopKilling(Game game) throws Exception {
        return false;
    }

    public boolean validateBottomKilling(Game game) throws Exception {
        return false;
    }

    public boolean killEnemyPiece(Game game) throws Exception {
        return false;
    }

    public List<String> possiblePrimaryMoves(Game game) throws Exception {
        return new ArrayList<>();
    }

    public Map<String, String> possibleKills(Game game) throws Exception {
        return new HashMap<>();
    }
}
