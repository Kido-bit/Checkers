package checkers.logic.piece;

import checkers.logic.board.Board;
import checkers.logic.board.Point;
import checkers.logic.game.Game;
import checkers.logic.game.GameStatusModule;
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
    public boolean hasMove(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        if (player.isWhite()) {
            if (board.pieceIsBlack(point)) {
                System.out.println("Not your piece!");
                return false;
            } else {
                if(!possiblePrimaryMoves(gameStatusModule).isEmpty()) {
                    return true;
                } return hasKill(gameStatusModule);
            }
        } else {
            if (board.pieceIsWhite(point)) {
                System.out.println("Not your piece!");
                return false;
            } else {
                if(!possiblePrimaryMoves(gameStatusModule).isEmpty()) {
                    return true;
                } return hasKill(gameStatusModule);
            }
        }
    }

    public List<String> possiblePrimaryMoves(GameStatusModule gameStatusModule) throws Exception {
        List<String> allPossibleMoves = new ArrayList<>();
        allPossibleMoves.addAll(upRightMoves(gameStatusModule));
        allPossibleMoves.addAll(upLeftMoves(gameStatusModule));
        allPossibleMoves.addAll(downRightMoves(gameStatusModule));
        allPossibleMoves.addAll(downLeftMoves(gameStatusModule));
        allPossibleMoves.forEach(System.out::println);
        return allPossibleMoves;
    }

    public List<String> upRightMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 7 && startY != 7) {
            for (int i = 1; i < 7 && startX + i <= 7 && startY + i <= 7; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(point.getPoint(i, i))) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY + i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(point.getPoint(i, i))) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY + i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> upLeftMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 0 && startY != 7) {
            for (int i = 1; i < 7 && startX - i >= 0 && startY + i <= 7; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(point.getPoint(-i, i))) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY + i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(point.getPoint(-i, i))) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY + i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> downRightMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 7 && startY != 0) {
            for (int i = 1; i < 7 && startX + i <= 7 && startY - i >= 0; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(point.getPoint(i, -i))) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY - i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(point.getPoint(i, -i))) {
                        possibleMoves.add(actualMove.append(startX + i).append(startY - i).toString());
                    }
                }
                previousMove.setLength(0);
                previousMove.append(actualMove);
            }
        }
        return possibleMoves;
    }

    public List<String> downLeftMoves(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        List<String> possibleMoves = new ArrayList<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder previousMove = new StringBuilder();
        if (startX != 0 && startY != 0) {
            for (int i = 1; i < 7 && startX - i >= 0 && startY - i >= 0; i++) {
                actualMove.setLength(0);
                if (i < 2) {
                    if (board.hasNoPiece(point.getPoint(-i, -i))) {
                        possibleMoves.add(actualMove.append(startX - i).append(startY - i).toString());
                    }
                } else {
                    if (possibleMoves.contains(previousMove.toString()) && board.hasNoPiece(point.getPoint(-i, -i))) {
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
    public boolean hasKill(GameStatusModule gameStatusModule) throws Exception {
        return !possibleKills(gameStatusModule).isEmpty();
    }

    @Override
    public boolean killEnemyPiece(GameStatusModule gameStatusModule) throws Exception {
        String killed = possibleKills(gameStatusModule).get(String.valueOf(gameStatusModule.getEndSpot().getPoint().getX()) +
                gameStatusModule.getEndSpot().getPoint().getY());
        if (killed == null) {
            return false;
        } else {
            gameStatusModule.getBoard().setBoardPieceNull(Integer.parseInt(String.valueOf(killed.charAt(0))),
                    Integer.parseInt(String.valueOf(killed.charAt(1))));
            return true;
        }
    }

    public Map<String, String> possibleKills(GameStatusModule gameStatusModule) throws Exception {
        Map<String, String> allPossibleKills = new HashMap<>();
        allPossibleKills.putAll(upRightKillUber(gameStatusModule));
        allPossibleKills.putAll(upLeftKillUber(gameStatusModule));
        allPossibleKills.putAll(downRightKillUber(gameStatusModule));
        allPossibleKills.putAll(downLeftKillUber(gameStatusModule));
        allPossibleKills.forEach((key, value) -> System.out.println(key + ":" + value));
        return allPossibleKills;
    }

    public Map<String, String> upRightKillUber(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX >= 6 || startY >= 6) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX + i <= 6 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(point.getPoint(i, i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(i, i)) && !board.getPiece(gameStatusModule).isWhite()) {
                        if (board.hasNoPiece(point.getPoint(i + 1, i + 1))) {
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
                    if (board.hasNoPiece(point.getPoint(i, i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(i, i)) && board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(i + 1, i + 1))) {
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

    public Map<String, String> upLeftKillUber(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX <= 1 || startY >= 6) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX - i >= 1 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(point.getPoint(-i, i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(-i, i)) && !board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(-i - 1, i + 1))) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY + i + 1).toString(),
                                killedPiece.append(startX - i).append(startY + i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX - i >= 1 && startY + i <= 6; i++) {
                    if (board.hasNoPiece(point.getPoint(-i, i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(-i, i)) && board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(-i - 1, i + 1))) {
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

    public Map<String, String> downRightKillUber(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX >= 6 || startY <= 1) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX + i <= 6 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(point.getPoint(i, -i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(i + 1, -i - 1)) && !board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(i + 1, -i - 1))) {
                        possibleKill.put(actualMove.append(startX + i + 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX + i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX + i <= 6 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(point.getPoint(i, -i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(i, -i)) && board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(i + 1, -i - 1))) {
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

    public Map<String, String> downLeftKillUber(GameStatusModule gameStatusModule) throws Exception {
        Board board = gameStatusModule.getBoard();
        Player player = gameStatusModule.getPlayer();
        Point point = gameStatusModule.getStartSpot().getPoint();
        int startX = gameStatusModule.getStartSpot().getPoint().getX();
        int startY = gameStatusModule.getStartSpot().getPoint().getY();
        possibleKill = new HashMap<>();
        StringBuilder actualMove = new StringBuilder();
        StringBuilder killedPiece = new StringBuilder();
        if (startX <= 1 || startY <= 1) {
            return possibleKill;
        } else {
            if (player.isWhite()) {
                for (int i = 1; i < 6 && startX - i >= 1 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(point.getPoint(-i, -i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(-i, -i)) && !board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(-i - 1, -i - 1))) {
                        possibleKill.put(actualMove.append(startX - i - 1).append(startY - i - 1).toString(),
                                killedPiece.append(startX - i).append(startY - i).toString());
                        return possibleKill;
                    } else {
                        return possibleKill;
                    }
                }
            } else {
                for (int i = 1; i < 6 && startX - i >= 1 && startY - i >= 1; i++) {
                    if (board.hasNoPiece(point.getPoint(-i, -i))) {
                        continue;
                    } else if (board.hasPiece(point.getPoint(-i, -i)) && board.getPiece(gameStatusModule).isWhite()
                            && board.hasNoPiece(point.getPoint(-i - 1, -i - 1))) {
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
