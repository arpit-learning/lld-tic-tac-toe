package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Move;
import dev.arpit.ticTacToe.models.Player;

public interface IMoveService {
  Move makeMove(Board board, Player player);
}
