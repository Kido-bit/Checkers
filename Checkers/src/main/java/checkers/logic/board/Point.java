package checkers.logic.board;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    int x;
    int y;

    public Point getPoint(int xChange, int yChange) {
        return new Point(x + xChange, y + getY());
    }
}
