package checkers.database.model;

import checkers.logic.game.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int moveNumber;
    private String playerName;
    private String startSpot;
    private String endSpot;
    private boolean isWhite;

    public MoveEntity (Game game) {
        this.moveNumber = game.getMoveCounter();
        this.playerName = game.getCurrentPlayer().getName();
        this.startSpot = game.getGameStatusModule().getStartSpot().toString();
        this.endSpot = game.getGameStatusModule().getEndSpot().toString();
        this.isWhite = game.getCurrentPlayer().isWhite();
    }
}
