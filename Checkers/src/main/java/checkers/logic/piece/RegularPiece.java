package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.board.Point;
import checkers.logic.board.Spot;
import checkers.logic.board.SpotFactory;
import checkers.logic.game.GameStatusModule;
import checkers.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RegularPiece extends Piece {
    private final boolean REGULAR = true;

    public RegularPiece(boolean white) {
        super(white);
    }

    @Override
    public boolean isRegular() {
        return REGULAR;
    }

    @Override
    public String getPieceIcon() {
        if (white) {
            return "\u25CF";
        } else {
            return "\u2B58";
        }
    }

    @Override
    public void advancePiece(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getEndSpot().getPoint();
        int x = gameStatusModule.getStartSpot().getPoint().getX();
        int y = gameStatusModule.getStartSpot().getPoint().getY();
        if (board.getPiece(gameStatusModule).isRegular()) {
            if (y == 7 && player.isWhite()) {
                board.setBoardSpot(x, y, SpotFactory.uberWhite(point));
            } else if (y == 0 && player.isBlack()) {
                board.setBoardSpot(x, y, SpotFactory.uberBlack(point));
            }
        }
    }

    @Override
    public boolean hasMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if (player.isWhite()) {
            if (validateTopKilling(gameStatusModule)) {
                return true;
            } else if (validateLeftMoves(gameStatusModule)) {
                return true;
            } else if (validateRightMoves(gameStatusModule)) {
                return true;
            } else if (validateCenterMoves(gameStatusModule)) {
                return true;
            } else {
                return true;
            }
        } else if (!player.isWhite()) {
            if (startY == 0) {
                if (hasKill(gameStatusModule)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 0 && (board.hasPiece(point.getPoint(1, -1)))) {
                if (hasKill(gameStatusModule)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 7 && (board.hasPiece(point.getPoint(-1, -1)))) {
                if (hasKill(gameStatusModule)) {
                    return true;
                } else {
                    System.out.println("No move available");
                }
            } else if (startX == 7 && (board.hasNoPiece(point.getPoint(-1, -1)))) {
                return true;
            } else if ((startX > 0 && startX < 7) &&
                    board.hasPiece(point.getPoint(1, -1)) &&
                    board.hasPiece(point.getPoint(-1, -1))) {
                if (hasKill(gameStatusModule)) {
                    return true;
                }
                System.out.println("No move available!");
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasKill(GameStatusModule gameStatusModule) throws Exception {
        if (validateExceptionsKilling(gameStatusModule)) {
            return true;
        } else if (validateCenterKilling(gameStatusModule)) {
            return true;
        } else if (validateLeftSideKilling(gameStatusModule)) {
            return true;
        } else if (validateRightSideKilling(gameStatusModule)) {
            return true;
        } else if (validateTopKilling(gameStatusModule)) {
            return true;
        } else return validateBottomKilling(gameStatusModule);
    }

    @Override
    public boolean upLeftKill(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (board.hasNoPiece(point.getPoint(-2, 2))) {
            if (player.isWhite()) {
                return board.pieceIsBlack(point.getPoint(-1, 1));
            } else {
                return board.pieceIsWhite(point.getPoint(-1, 1));
            }
        }
        return false;
    }

    @Override
    public boolean upRightKill(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (board.hasNoPiece(point.getPoint(2, 2))) {
            if (player.isWhite()) {
                return board.pieceIsBlack(point.getPoint(1, 1));
            } else {
                return board.pieceIsWhite(point.getPoint(1, 1));
            }
        }
        return false;
    }

    @Override
    public boolean downLeftKill(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (board.hasNoPiece(point.getPoint(-2, -2))) {
            if (player.isWhite()) {
                return board.pieceIsBlack(point.getPoint(-1, -1));
            } else {
                return board.pieceIsWhite(point.getPoint(-1, -1));
            }
        }
        return false;
    }

    @Override
    public boolean downRightKill(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (board.hasNoPiece(point.getPoint(2, -2))) {
            if (player.isWhite()) {
                return board.pieceIsBlack(point.getPoint(1, -1));
            } else {
                return board.pieceIsWhite(point.getPoint(1, -1));
            }
        }
        return false;
    }

    @Override
    public boolean validateExceptionsKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if ((startX == 0 && startY == 0) || (startX == 1 && startY == 1)) {
            return (upRightKill(gameStatusModule));
        } else if ((startX == 6 && startY == 0) || (startX == 7 && startY == 1)) {
            return (upLeftKill(gameStatusModule));
        } else if ((startX == 0 && startY == 6) || (startX == 1 && startY == 7)) {
            return (downRightKill(gameStatusModule));
        } else if ((startX == 6 && startY == 6) || (startX == 7 && startY == 7)) {
            return (downLeftKill(gameStatusModule));
        }
        return false;
    }

    @Override
    public boolean validateCenterKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if (startX > 1 && startY > 1 && startX < 6 && startY < 6) {
            if (upLeftKill(gameStatusModule)) {
                return true;
            } else if (upRightKill(gameStatusModule)) {
                return true;
            } else if (downLeftKill(gameStatusModule)) {
                return true;
            } else {
                return (downRightKill(gameStatusModule));
            }
        }
        return false;
    }

    @Override
    public boolean validateLeftSideKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if ((startX == 0 && (startY == 2 || startY == 4)) || (startX == 1 && (startY == 3 || startY == 5))) {
            if (upRightKill(gameStatusModule)) {
                return true;
            } else {
                return (downRightKill(gameStatusModule));
            }
        }
        return false;
    }

    @Override
    public boolean validateRightSideKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if ((startX == 7 && (startY == 3 || startY == 5)) || (startX == 6 && (startY == 2 || startY == 4))) {
            if (upLeftKill(gameStatusModule)) {
                return true;
            } else {
                return (downLeftKill(gameStatusModule));
            }
        }
        return false;
    }

    @Override
    public boolean validateTopKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if ((startY == 7 && (startX == 3 || startX == 5))
                || (startY == 6 && (startX == 2 || startX == 4))) {
            if (downLeftKill(gameStatusModule)) {
                return true;
            } else {
                return (downRightKill(gameStatusModule));
            }
        }
        return false;
    }

    @Override
    public boolean validateBottomKilling(GameStatusModule gameStatusModule) throws Exception {
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        if ((startY == 0 && (startX == 2 || startX == 4)) || (startY == 1 && (startX == 3 || startX == 5))) {
            if (upRightKill(gameStatusModule)) {
                return true;
            } else {
                return (upLeftKill(gameStatusModule));
            }
        }
        return false;
    }

    @Override
    public boolean killEnemyPiece(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point startPoint = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        int endX = gameStatusModule.getEndSpot().getPoint().getX();
        int endY = gameStatusModule.getEndSpot().getPoint().getY();
        if (player.isWhite()) {
            if (startX - 2 == endX && startY + 2 == endY && board.pieceIsBlack(startPoint.getPoint(-1, 1))) {
                board.setBoardPieceNull(startX - 1, startY + 1);
                return true;
            } else if ((startX + 2 == endX && startY + 2 == endY) && board.pieceIsBlack(startPoint.getPoint(1, 1))) {
                board.setBoardPieceNull(startX + 1, startY + 1);
                return true;
            } else if ((startX - 2 == endX && startY - 2 == endY) && board.pieceIsBlack(startPoint.getPoint(-1, -1))) {
                board.setBoardPieceNull(startX - 1, startY - 1);
                return true;
            } else if ((startX + 2 == endX && startY - 2 == endY) && board.pieceIsBlack(startPoint.getPoint(1, -1))) {
                board.setBoardPieceNull(startX + 1, startY - 1);
                return true;
            }
        } else if (!player.isWhite()) {
            if (startX - 2 == endX && startY + 2 == endY && board.pieceIsWhite(startPoint.getPoint(-1, 1))) {
                board.setBoardPieceNull(startX - 1, startY + 1);
                return true;
            } else if ((startX + 2 == endX && startY + 2 == endY) && board.pieceIsWhite(startPoint.getPoint(1, 1))) {
                board.setBoardPieceNull(startX + 1, startY + 1);
                return true;
            } else if ((startX - 2 == endX && startY - 2 == endY) && board.pieceIsWhite(startPoint.getPoint(-1, -1))) {
                board.setBoardPieceNull(startX - 1, startY - 1);
                return true;
            } else if ((startX + 2 == endX && startY - 2 == endY) && board.pieceIsWhite(startPoint.getPoint(1, -1))) {
                board.setBoardPieceNull(startX + 1, startY - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> possiblePrimaryMoves(GameStatusModule gameStatusModule) {
        return new ArrayList<>();
    }
}
