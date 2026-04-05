package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.exceptions.EmptyMovesException;
import dev.arpit.ticTacToe.exceptions.GameDrawnException;
import dev.arpit.ticTacToe.exceptions.GameInvalidationException;
import dev.arpit.ticTacToe.exceptions.InvalidMoveException;
import dev.arpit.ticTacToe.models.*;

import java.util.List;

public interface IGameService {
  Game buildGame(int dimension, List<Player> players) throws GameInvalidationException;
  void makeMove(Game game) throws InvalidMoveException, GameDrawnException;
  Player getWinner(Game game);
  void undo(Game game) throws EmptyMovesException;
}
