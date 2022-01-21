package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.board.Point;
import checkers.logic.game.Game;
import checkers.logic.game.GameStatusModule;
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

    public abstract boolean hasMove(GameStatusModule gameStatusModule) throws Exception;

    public boolean hasKill(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public void advancePiece(GameStatusModule gameStatusModule) throws Exception {
    }

    public boolean upLeftMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        return (board.hasNoPiece(point.getPoint(-1, 1)));
    }

    public boolean upRightMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        return (board.hasNoPiece(point.getPoint(1, 1)));

    }

    public boolean downLeftMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        return (board.hasNoPiece(point.getPoint(-1, -1)));
    }

    public boolean downRightMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        return (board.hasNoPiece(point.getPoint(1, -1)));
    }

    public boolean validateExceptionsMoves(GameStatusModule gameStatusModule) {
        return false;
    }

    public boolean validateCenterMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();

        if (board.hasPiece(point.getPoint(1, 1)) && board.hasPiece(point.getPoint(-1, 1))) {
            if (hasKill(gameStatusModule)) {
                return true;
            } else {
                System.out.println("No move available!");
            }
        }
            return false;
    }

    public boolean validateLeftMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if (startX == 0 && board.hasNoPiece(point.getPoint(1, 1))) {
            return true;
        }
        if (startX == 0 && board.hasPiece(point.getPoint(1, 1))) {
            if (hasKill(gameStatusModule)) {
                return true;
            } else {
                System.out.println("No move available!");
            }
        }
        return false;
    }

    public boolean validateRightMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();

        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if (startX == 7 && (board.hasNoPiece(point.getPoint(-1, 1)))) {
            return true;
        }
        if (startX == 7 && board.hasPiece(point.getPoint(-1, 1))) {
            if (hasKill(gameStatusModule)) {
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

    public boolean validateBottomMoves(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean upLeftKill(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean upRightKill(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean downLeftKill(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean downRightKill(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateExceptionsKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateCenterKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateLeftSideKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateRightSideKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateTopKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean validateBottomKilling(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public boolean killEnemyPiece(GameStatusModule gameStatusModule) throws Exception {
        return false;
    }

    public List<String> possiblePrimaryMoves(GameStatusModule gameStatusModule) throws Exception {
        return new ArrayList<>();
    }

    public Map<String, String> possibleKills(GameStatusModule gameStatusModule) throws Exception {
        return new HashMap<>();
    }
}
