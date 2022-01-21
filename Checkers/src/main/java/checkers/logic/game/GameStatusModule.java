package checkers.logic.game;

import checkers.logic.board.Board;
import checkers.logic.board.Point;
import checkers.logic.board.Spot;
import checkers.logic.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GameStatusModule {
    private Board board;
    public Player player;
    private Spot startSpot;
    private Spot endSpot;

    public GameStatusModule(Game game) {
        this.board = game.getBoard();
        this.player = game.getCurrentPlayer();
        this.startSpot = board.getBoardSpot();
        this.endSpot = board.getBoardSpot();
    }
}
