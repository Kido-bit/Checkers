package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.game.Game;
import checkers.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UberPiece extends Piece {
    private final boolean REGULAR = false;
    Map<String, String> possibleKill;

    public UberPiece(boolean white) {
        super(white);
    }

    @Override
    public boolean isRegular() {
        return REGULAR;
    }

    @Override
    public String getPieceIcon() {
        if (white) {
            return "\u25A0";
        } else {
            return "\u25A1";
        }
    }

    @Override
    public boolean hasMove(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        if (player.isWhite()) {
            if (board.pieceIsBlack(startX, startY)) {
                System.out.println("Not your piece!");
                return false;
            } else {
                if(!possiblePrimaryMoves(game).isEmpty()) {
                    return true;
                } return hasKill(game);
            }
        } else {
            if (board.pieceIsWhite(startX, startY)) {
                System.out.println("Not your piece!");
                return false;
            } else {
                if(!possiblePrimaryMoves(game).isEmpty()) {
                    return true;
                } return hasKill(game);
            }
        }
    }

    public List<String> possiblePrimaryMoves(Game game) throws Exception {
        List<String> allPossibleMoves = new ArrayList<>();
        allPossibleMoves.addAll(upRightMoves(game));
        allPossibleMoves.addAll(upLeftMoves(game));
        allPossibleMoves.addAll(downRightMoves(game));
        allPossibleMoves.addAll(downLeftMoves(game));
        allPossibleMoves.forEach(System.out::println);
        return allPossibleMoves;
    }

    public List<String> upRightMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 7 && startY != 7) {
            for (int i = 1; i < 7 && startX + i <= 7 && startY + i <= 7; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(startX + i, startY + i)) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY + i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(startX + i, startY + i)) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY + i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> upLeftMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 0 && startY != 7) {
            for (int i = 1; i < 7 && startX - i >= 0 && startY + i <= 7; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(startX - i, startY + i)) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY + i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(startX - i, startY + i)) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY + i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> downRightMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 7 && startY != 0) {
            for (int i = 1; i < 7 && startX + i <= 7 && startY - i >= 0; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(startX + i, startY - i)) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY - i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(startX + i, startY - i)) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY - i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> downLeftMoves(Game game) throws Exception {
        Board board = game.board;
        int startX = game.startX;
        int startY = game.startY;
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 0 && startY != 0) {
            for (int i = 1; i < 7 && startX - i >= 0 && startY - i >= 0; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(startX - i, startY - i)) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY - i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(startX - i, startY - i)) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY - i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    @Override
    public boolean hasKill(Game game) throws Exception {
        return !possibleKills(game).isEmpty();
    }

    @Override
    public boolean killEnemyPiece(Game game) throws Exception {
        String killed = possibleKills(game).get(String.valueOf(game.endX) + game.endY);
        if (killed == null) {
            return false;
        } else {
            game.board.setBoardPieceNull(Integer.parseInt(String.valueOf(killed.charAt(0))),
                    Integer.parseInt(String.valueOf(killed.charAt(1))));
            return true;
        }
    }

    public Map<String, String> possibleKills(Game game) throws Exception {
        Map<String, String> allPossibleKills = new HashMap<>();
        allPossibleKills.putAll(upRightKillUber(game));
        allPossibleKills.putAll(upLeftKillUber(game));
        allPossibleKills.putAll(downRightKillUber(game));
        allPossibleKills.putAll(downLeftKillUber(game));
        allPossibleKills.forEach((key, value) -> System.out.println(key + ":" + value));
        return allPossibleKills;
    }

    public Map<String, String> upRightKillUber(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX >= 6 || startY >= 6) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX + i <= 6 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(startX + i, startY + i)) {
                        continue;
                    } else if (board.hasPiece(startX + i, startY + i) && !board.getPiece(game.startX + i, game.startY + i).isWhite()) {
                        if (board.hasNoPiece(startX + i + 1, startY + i + 1)) {
                            possibleKill.put(actualMove.append(startX + i + 1).append(startY + i + 1).toString(),
                                    killedPiece.append(startX + i).append(startY + i).toString());
                            return possibleKill;
                        }
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX + i <= 6 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(startX + i, startY + i)) {
                        continue;
                    } else if (board.hasPiece(startX + i, startY + i) && board.getPiece(startX + i, startY + i).isWhite()
                            && board.hasNoPiece(startX + i + 1, startY + i + 1)) {
                        possibleKill.put(actualMove.append(startX + i + 1).append(startY + i + 1).toString(),
                                killedPiece.append(startX + i).append(startY + i).toString());
                        return possibleKill;

                    } else {
                        return possibleKill;
                    }
                }
            }
        }
        return possibleKill;
    }

    public Map<String, String> upLeftKillUber(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX <= 1 || startY >= 6) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX - i >= 1 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(startX - i, startY + i)) {
                        continue;
                    } else if (board.hasPiece(startX - i, startY + i) && !board.getPiece(startX - i, startY + i).isWhite()
                            && board.hasNoPiece(startX - i - 1, startY + i + 1)) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY + i + 1).toString(),
                                killedPiece.append(startX - i).append(startY + i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX - i >= 1 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(startX - i, startY + i)) {
                        continue;
                    } else if (board.hasPiece(startX - i, startY + i) && board.getPiece(startX - i, startY + i).isWhite()
                            && board.hasNoPiece(startX - i - 1, startY + i + 1)) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY + i + 1).toString(),
                                killedPiece.append(startX - i).append(startY + i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            }
        }
        return possibleKill;
    }

    public Map<String, String> downRightKillUber(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX >= 6 || startY <= 1) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX + i <= 6 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(startX + i, startY - i)) {
                        continue;
                    } else if (board.hasPiece(startX + i, startY - i) && !board.getPiece(startX + i, startY - i).isWhite()
                            && board.hasNoPiece(startX + i + 1, startY - i - 1)) {
                        possibleKill.put(actualMove.append(startX + i + 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX + i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX + i <= 6 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(startX + i, startY - i)) {
                        continue;
                    } else if (board.hasPiece(startX + i, startY - i) && board.getPiece(startX + i, startY - i).isWhite()
                            && board.hasNoPiece(startX + i + 1, startY - i - 1)) {
                        possibleKill.put(actualMove.append(startX + i + 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX + i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            }
        }
        return possibleKill;
    }

    public Map<String, String> downLeftKillUber(Game game) throws Exception {
        Board board = game.board;
        Player player = game.currentPlayer;
        int startX = game.startX;
        int startY = game.startY;
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX <= 1 || startY <= 1) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX - i >= 1 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(startX - i, startY - i)) {
                        continue;
                    } else if (board.hasPiece(startX - i, startY - i) && !board.getPiece(startX - i, startY - i).isWhite()
                            && board.hasNoPiece(startX - i - 1, startY - i - 1)) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX - i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX - i >= 1 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(startX - i, startY - i)) {
                        continue;
                    } else if (board.hasPiece(startX - i, startY - i) && board.getPiece(startX - i, startY - i).isWhite()
                            && board.hasNoPiece(startX - i - 1, startY - i - 1)) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX - i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            }
        }
        return possibleKill;
    }
}
