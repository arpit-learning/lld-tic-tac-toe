package dev.arpit.ticTacToe.services.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import dev.arpit.ticTacToe.dtos.ResponseCode;
import dev.arpit.ticTacToe.exceptions.GameDrawnException;
import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Move;
import dev.arpit.ticTacToe.models.Symbol;

/**
 * Alternative win detection with corner rule and draw detection via {@code validHMs}.
 * Not {@code @Primary} — inject with {@code @Qualifier("hashMapWinningStrategy")} if you want this
 * implementation instead of {@link SimpleWinningStrategy}.
 * <p>
 * State is (re)initialized when the board dimension changes (e.g. new game with different size).
 */
@Component("hashMapWinningStrategy")
@Primary
public class ShortCircuitWinningStrategy implements IWinningStrategy {

  private List<Map<Symbol, Integer>> rowHMList;
  private List<Map<Symbol, Integer>> colHMList;
  private Map<Symbol, Integer> leftDiagHM;
  private Map<Symbol, Integer> rightDiagHM;
  private Map<Symbol, Integer> cornerHM;
  private int validHMs;
  private int dimension = -1;

  private void ensureInitialized(Board board) {
    int d = board.getDimension();
    if (dimension == d && rowHMList != null) {
      return;
    }
    dimension = d;
    rowHMList = new ArrayList<>();
    colHMList = new ArrayList<>();
    leftDiagHM = new HashMap<>();
    rightDiagHM = new HashMap<>();
    cornerHM = new HashMap<>();
    validHMs = 2 * dimension + 3;
    for (int i = 0; i < dimension; i++) {
      rowHMList.add(new HashMap<>());
      colHMList.add(new HashMap<>());
    }
  }

  private boolean checkAndUpdateHMList(Map<Symbol, Integer> hM, Symbol symbol, int checkCount) throws GameDrawnException {
    if (hM.containsKey(symbol)) {
      hM.put(symbol, hM.get(symbol) + 1);
      if (hM.get(symbol) == checkCount) {
        return true;
      }
    } else {
      hM.put(symbol, 1);
    }

    if (hM.get(symbol) > 1) {
      validHMs--;
      if (validHMs == 0) {
        throw new GameDrawnException(
            ResponseCode.TTT_FAILURE,
            "Game is draw",
            "Game is draw"
        );
      }
    }

    return false;
  }

  private boolean checkAndUpdateRowHMList(int row, Symbol symbol) throws GameDrawnException {
    Map<Symbol, Integer> hM = rowHMList.get(row);
    return checkAndUpdateHMList(hM, symbol, dimension);
  }

  private boolean checkAndUpdateColHMList(int col, Symbol symbol) throws GameDrawnException {
    Map<Symbol, Integer> hM = colHMList.get(col);
    return checkAndUpdateHMList(hM, symbol, dimension);
  }

  private boolean checkLeftDiagHM(int row, int col) {
    return row == col;
  }

  private boolean checkRightDiagHM(int row, int col) {
    return row + col == dimension - 1;
  }

  private boolean checkCornerHM(int row, int col) {
    return ((row == 0 && col == 0) ||
        (row == 0 && col == dimension - 1) ||
        (row == dimension - 1 && col == 0) ||
        (row == dimension - 1 && col == dimension - 1)
    );
  }

  private boolean checkAndUpdateLeftDiagHM(int row, int col, Symbol symbol) throws GameDrawnException {
    if (!checkLeftDiagHM(row, col)) {
      return false;
    }
    return checkAndUpdateHMList(leftDiagHM, symbol, dimension);
  }

  private boolean checkAndUpdateRightDiagHM(int row, int col, Symbol symbol) throws GameDrawnException {
    if (!checkRightDiagHM(row, col)) {
      return false;
    }
    return checkAndUpdateHMList(rightDiagHM, symbol, dimension);
  }

  private boolean checkAndUpdateCornerHM(int row, int col, Symbol symbol) throws GameDrawnException {
    if (!checkCornerHM(row, col)) {
      return false;
    }
    return checkAndUpdateHMList(cornerHM, symbol, 4);
  }

  @Override
  public boolean checkWinner(Board board, Move move) throws GameDrawnException {
    ensureInitialized(board);
    int row = move.getCell().getRow();
    int col = move.getCell().getCol();
    Symbol symbol = move.getPlayer().getSymbol();

    return checkAndUpdateRowHMList(row, symbol) ||
        checkAndUpdateColHMList(col, symbol) ||
        checkAndUpdateLeftDiagHM(row, col, symbol) ||
        checkAndUpdateRightDiagHM(row, col, symbol) ||
        checkAndUpdateCornerHM(row, col, symbol);
  }
}
