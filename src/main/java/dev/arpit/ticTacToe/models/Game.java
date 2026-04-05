package dev.arpit.ticTacToe.models;

import dev.arpit.ticTacToe.dtos.ResponseCode;
import dev.arpit.ticTacToe.exceptions.GameInvalidationException;
import dev.arpit.ticTacToe.models.constants.GameState;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class Game extends BaseModel {
  private Board board;
  private List<Player> players;
  private GameState gameState;
  private Player winner;
  private int nextPlayerMoveIndex;
  private List<Move> moves;

  Game(int dimension, List<Player> players) {
    this.board = new Board(dimension);
    this.players = players;
    this.nextPlayerMoveIndex = 0;
    this.moves = new ArrayList<>();
    this.gameState = GameState.IN_PROGRESS;
  }

  public static Builder getBuilder() {
        return new Builder();
    }

  public static class Builder {
    private int dimension;
    private List<Player> players;

    public Builder setDimension(int dimension) {
      this.dimension = dimension;
      return this;
    }

    public Builder setPlayers(List<Player> players) {
      this.players = players;
      return this;
    }

    private boolean validate() {
        return true;
    }

    public Game build() throws GameInvalidationException {
      // validate.
      if (!validate()) {
        throw new GameInvalidationException(
            ResponseCode.TTT_FAILURE,
            "Invalid game",
            "Invalid game"
        );
      }

      //create the Game object.
      return new Game(dimension, players);
    }
  }
}
