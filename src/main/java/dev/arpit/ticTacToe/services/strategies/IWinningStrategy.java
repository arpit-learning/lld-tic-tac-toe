package dev.arpit.ticTacToe.services.strategies;

import dev.arpit.ticTacToe.exceptions.GameDrawnException;
import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Move;

public interface IWinningStrategy {
  boolean checkWinner(Board board, Move move) throws GameDrawnException;
}
