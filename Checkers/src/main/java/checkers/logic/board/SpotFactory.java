package checkers.logic.board;

import checkers.logic.piece.RegularPiece;
import checkers.logic.piece.UberPiece;

public class SpotFactory {
    public static Spot regularWhite(Point point) {
        return new Spot(point, new RegularPiece(true));
    }
    public static Spot regularBlack(Point point) {
        return new Spot(point, new RegularPiece(false));
    }
    public static Spot uberWhite(Point point) {
        return new Spot(point, new UberPiece(true));
    }
    public static Spot uberBlack(Point point) {
        return new Spot(point, new UberPiece(false));
    }
    public static Spot emptySpot(Point point) {
        return new Spot(point, null);
    }
}
