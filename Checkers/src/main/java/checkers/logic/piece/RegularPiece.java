package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.game.Game;
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
    public boolean hasMove(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (player.isWhite()) {
            if (startY == 7) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 0 && board.hasPiece(startX + 1, startY + 1)) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 7 && (board.hasPiece(startX - 1, startY + 1))) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 7 && (board.hasNoPiece(startX - 1, startY + 1))) {
                return true;
            } else if (
                    board.hasPiece(startX + 1, startY + 1) &&
                            board.hasPiece(startX - 1, startY + 1)) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else {
                return true;
            }
        } else if (!player.isWhite()) {
            if (startY == 0) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 0 && (board.hasPiece(startX + 1, startY - 1))) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available!");
                }
            } else if (startX == 7 && (board.hasPiece(startX - 1, startY - 1))) {
                if (hasKill(game)) {
                    return true;
                } else {
                    System.out.println("No move available");
                }
            } else if (startX == 7 && (board.hasNoPiece(startX - 1, startY - 1))) {
                return true;
            } else if ((startX > 0 && startX < 7) &&
                    board.hasPiece(startX + 1, startY - 1) &&
                    board.hasPiece(startX - 1, startY - 1)) {
                if (hasKill(game)) {
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
    public boolean hasKill(Game game) throws Exception {
        if (validateExceptionsKilling(game)) {
            return true;
        } else if (validateCenterKilling(game)) {
            return true;
        } else if (validateLeftSideKilling(game)) {
            return true;
        } else if (validateRightSideKilling(game)) {
            return true;
        } else if (validateTopKilling(game)) {
            return true;
        } else return validateBottomKilling(game);
    }

    @Override
    public boolean upLeftKill(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (board.hasNoPiece(startX - 2, startY + 2)) {
            if (player.isWhite()) {
                return board.pieceIsBlack(startX - 1, startY + 1);
            } else {
                return board.pieceIsWhite(startX - 1, startY + 1);
            }
        }
        return false;
    }

    @Override
    public boolean upRightKill(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (board.hasNoPiece(startX + 2, startY + 2)) {
            if (player.isWhite()) {
                return board.pieceIsBlack(startX + 1, startY + 1);
            } else {
                return board.pieceIsWhite(startX + 1, startY + 1);
            }
        }
        return false;
    }

    @Override
    public boolean downLeftKill(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (board.hasNoPiece(startX - 2, startY - 2)) {
            if (player.isWhite()) {
                return board.pieceIsBlack(startX - 1, startY - 1);
            } else {
                return board.pieceIsWhite(startX - 1, startY - 1);
            }
        }
        return false;
    }

    @Override
    public boolean downRightKill(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (board.hasNoPiece(startX + 2, startY - 2)) {
            if (player.isWhite()) {
                return board.pieceIsBlack(startX + 1, startY - 1);
            } else {
                return board.pieceIsWhite(startX + 1, startY - 1);
            }
        }
        return false;
    }

    @Override
    public boolean validateExceptionsKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if ((startX == 0 && startY == 0) || (startX == 1 && startY == 1)) {
            return (upRightKill(game));
        } else if ((startX == 6 && startY == 0) || (startX == 7 && startY == 1)) {
            return (upLeftKill(game));
        } else if ((startX == 0 && startY == 6) || (startX == 1 && startY == 7)) {
            return (downRightKill(game));
        } else if ((startX == 6 && startY == 6) || (startX == 7 && startY == 7)) {
            return (downLeftKill(game));
        }
        return false;
    }

    @Override
    public boolean validateCenterKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if (startX > 1 && startY > 1 && startX < 6 && startY < 6) {
            if (upLeftKill(game)) {
                return true;
            } else if (upRightKill(game)) {
                return true;
            } else if (downLeftKill(game)) {
                return true;
            } else {
                return (downRightKill(game));
            }
        }
        return false;
    }

    @Override
    public boolean validateLeftSideKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if ((startX == 0 && (startY == 2 || startY == 4)) || (startX == 1 && (startY == 3 || startY == 5))) {
            if (upRightKill(game)) {
                return true;
            } else {
                return (downRightKill(game));
            }
        }
        return false;
    }

    @Override
    public boolean validateRightSideKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if ((startX == 7 && (startY == 3 || startY == 5)) || (startX == 6 && (startY == 2 || startY == 4))) {
            if (upLeftKill(game)) {
                return true;
            } else {
                return (downLeftKill(game));
            }
        }
        return false;
    }

    @Override
    public boolean validateTopKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if ((startY == 7 && (startX == 3 || startX == 5))
                || (startY == 6 && (startX == 2 || startX == 4))) {
            if (downLeftKill(game)) {
                return true;
            } else {
                return (downRightKill(game));
            }
        }
        return false;
    }

    @Override
    public boolean validateBottomKilling(Game game) throws Exception {
        int startX = game.startX;
        int startY = game.startY;
        if ((startY == 0 && (startX == 2 || startX == 4)) || (startY == 1 && (startX == 3 || startX == 5))) {
            if (upRightKill(game)) {
                return true;
            } else {
                return (upLeftKill(game));
            }
        }
        return false;
    }

    @Override
    public boolean killEnemyPiece(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        int endX = game.endX;
        int endY = game.endY;
        if (player.isWhite()) {
            if (startX - 2 == endX && startY + 2 == endY && board.pieceIsBlack(startX - 1, startY + 1)) {
                board.setBoardPieceNull(startX - 1, startY + 1);
                return true;
            } else if ((startX + 2 == endX && startY + 2 == endY) && board.pieceIsBlack(startX + 1, startY + 1)) {
                board.setBoardPieceNull(startX + 1, startY + 1);
                return true;
            } else if ((startX - 2 == endX && startY - 2 == endY) && board.pieceIsBlack(startX - 1, startY - 1)) {
                board.setBoardPieceNull(startX - 1, startY - 1);
                return true;
            } else if ((startX + 2 == endX && startY - 2 == endY) && board.pieceIsBlack(startX + 1, startY - 1)) {
                board.setBoardPieceNull(startX + 1, startY - 1);
                return true;
            }
        } else if (!player.isWhite()) {
            if (startX - 2 == endX && startY + 2 == endY && board.pieceIsWhite(startX - 1, startY + 1)) {
                board.setBoardPieceNull(startX - 1, startY + 1);
                return true;
            } else if ((startX + 2 == endX && startY + 2 == endY) && board.pieceIsWhite(startX + 1, startY + 1)) {
                board.setBoardPieceNull(startX + 1, startY + 1);
                return true;
            } else if ((startX - 2 == endX && startY - 2 == endY) && board.pieceIsWhite(startX - 1, startY - 1)) {
                board.setBoardPieceNull(startX - 1, startY - 1);
                return true;
            } else if ((startX + 2 == endX && startY - 2 == endY) && board.pieceIsWhite(startX + 1, startY - 1)) {
                board.setBoardPieceNull(startX + 1, startY - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> possiblePrimaryMoves(Game game) {
        return new ArrayList<>();
    }
}
