package dev.arpit.ticTacToe.controllers;

import dev.arpit.ticTacToe.dtos.ResponseDto;
import dev.arpit.ticTacToe.models.Game;
import dev.arpit.ticTacToe.models.constants.GameState;
import dev.arpit.ticTacToe.models.Player;

import java.util.List;

public interface IGameController {
  ResponseDto<Game> startGame(int dimension, List<Player> players);
  ResponseDto<Void> makeMove(Game game);
  ResponseDto<Player> getWinner(Game game);
  ResponseDto<Void> undo(Game game);
  ResponseDto<GameState> getGameState(Game game);
}
