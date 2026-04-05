package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.models.Board;

public interface IBoardService {
  Board createBoard(int dimension);
  void displayBoard(Board board);
}
