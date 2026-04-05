package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Cell;
import dev.arpit.ticTacToe.models.constants.CellState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService implements IBoardService {
  @Override
  public Board createBoard (int dimension) {
    return new Board(dimension);
  }

  @Override
  public void displayBoard(Board board) {
    int dimension = board.getDimension();
    List<List<Cell>> matrix = board.getMatrix();
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        Cell cell = matrix.get(i).get(j);
        if (cell.getCellState().equals(CellState.EMPTY)) {
          System.out.print("| |");
        } else {
          System.out.print("|" + cell.getPlayer().getSymbol().getValue() + "|");
        }
      }
      System.out.println();
    }
  }
}
