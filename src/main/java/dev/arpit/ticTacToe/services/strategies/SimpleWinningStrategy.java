package dev.arpit.ticTacToe.services.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import dev.arpit.ticTacToe.exceptions.GameDrawnException;
import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Move;
import dev.arpit.ticTacToe.models.Symbol;

/**
 * Default win detection (incremental row/column/diagonal counts). Marked {@link Primary} when multiple
 * {@link IWinningStrategy} beans are present.
 */
@Component("simpleWinningStrategy")
public class SimpleWinningStrategy implements IWinningStrategy {
  private List<Map<Symbol, Integer>> rowHMs;
  private List<Map<Symbol, Integer>> colHMs;
  private Map<Symbol, Integer> leftDiagHM;
  private Map<Symbol, Integer> rightDiagHM;
  private int dimension;

  private boolean checkAndUpdateHM(Map<Symbol, Integer> hM, Symbol symbol, int checkCount) {
    if (hM.containsKey(symbol)) {
      hM.put(symbol, hM.get(symbol) + 1);
      return hM.get(symbol) == checkCount;
    } else {
      hM.put(symbol, 1);
    }

    return false;
  }

  private void ensureInitialized(Board board) {
    int d = board.getDimension();
    if (dimension == 0 && rowHMs != null) {
      return;
    }

    dimension = d;
    rowHMs = new ArrayList<>();
    colHMs = new ArrayList<>();
    leftDiagHM = new HashMap<>();
    rightDiagHM = new HashMap<>();
    for (int i = 0; i < d; i++) {
      rowHMs.add(new HashMap<>());
      colHMs.add(new HashMap<>());
    }
  }

  private boolean checkLeftDiagHM(int row, int col) {
    return row == col;
  }

  private boolean checkRightDiagHM(int row, int col) {
    return row + col == dimension - 1;
  }


  private boolean checkAndUpdateLeftDiagHM(int row, int col, Symbol symbol) {
    if (!checkLeftDiagHM(row, col)) {
      return false;
    }
    return checkAndUpdateHM(leftDiagHM, symbol, dimension);
  }

  private boolean checkAndUpdateRightDiagHM(int row, int col, Symbol symbol) {
    if (!checkRightDiagHM(row, col)) {
      return false;
    }
    return checkAndUpdateHM(rightDiagHM, symbol, dimension);
  }

  @Override
  public boolean checkWinner(Board board, Move move) {
    ensureInitialized(board);
    int row = move.getCell().getRow();
    int col = move.getCell().getCol();
    Symbol symbol = move.getPlayer().getSymbol();

    Map<Symbol, Integer> rowHM = rowHMs.get(row);
    Map<Symbol, Integer> colHM = colHMs.get(col);

    return checkAndUpdateHM(rowHM, symbol, dimension) ||
        checkAndUpdateHM(colHM, symbol, dimension) ||
        checkAndUpdateLeftDiagHM(row, col, symbol) ||
        checkAndUpdateRightDiagHM(row, col, symbol);
  }
}
