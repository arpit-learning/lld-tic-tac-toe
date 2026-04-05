package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.dtos.ResponseCode;
import dev.arpit.ticTacToe.exceptions.EmptyMovesException;
import dev.arpit.ticTacToe.exceptions.GameDrawnException;
import dev.arpit.ticTacToe.exceptions.GameInvalidationException;
import dev.arpit.ticTacToe.exceptions.InvalidMoveException;
import dev.arpit.ticTacToe.models.*;
import dev.arpit.ticTacToe.models.constants.CellState;
import dev.arpit.ticTacToe.models.constants.GameState;
import dev.arpit.ticTacToe.services.strategies.IWinningStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService implements IGameService {

  private final IMoveService iMoveService;
  private final IWinningStrategy iWinningStrategy;

  public GameService(IMoveService iMoveService, IWinningStrategy iWinningStrategy) {
    this.iMoveService = iMoveService;
    this.iWinningStrategy = iWinningStrategy;
  }

  @Override
  public Game buildGame(int dimension, List<Player> players) throws GameInvalidationException {
    return Game.getBuilder()
        .setDimension(dimension)
        .setPlayers(players)
        .build();
  }

  private boolean checkWinner(Board board, Move move) throws GameDrawnException {
    return iWinningStrategy.checkWinner(board, move);
  }

  private boolean validateMove(Board board, Move move) {
    int row = move.getCell().getRow();
    int col = move.getCell().getCol();

    return row < board.getDimension() && row >= 0 && col < board.getDimension() && col >= 0 &&
        board.getMatrix().get(row).get(col).getCellState().equals(CellState.EMPTY);
  }

  @Override
  public void makeMove(Game game) throws InvalidMoveException, GameDrawnException {
    Player currentPlayer = game.getPlayers().get(game.getNextPlayerMoveIndex());

    System.out.println("It is " + currentPlayer.getName() + "'s move.");
    Move move = iMoveService.makeMove(game.getBoard(), currentPlayer);

    System.out.println(currentPlayer.getName() + " has made a move at Row: " + move.getCell().getRow() +
        ", col: " + move.getCell().getCol() + ".");

    // Validate the move before we apply the move on Board.
    if (!validateMove(game.getBoard(), move)) {
      System.out.println("Invalid move by player: " + currentPlayer.getName());
      throw new InvalidMoveException(
          ResponseCode.TTT_FAILURE,
          "Invalid move made by player: " + currentPlayer.getName(),
          "Invalid move made by player"
      );
    }

    int row = move.getCell().getRow();
    int col = move.getCell().getCol();
    Cell finalCellToMakeMove = game.getBoard().getMatrix().get(row).get(col);
    finalCellToMakeMove.setCellState(CellState.FILLED);
    finalCellToMakeMove.setPlayer(currentPlayer);

    Move finalMove = new Move(currentPlayer, finalCellToMakeMove);
    game.getMoves().add(finalMove);

    int nextPlayerMoveIndex = game.getNextPlayerMoveIndex() + 1;
    nextPlayerMoveIndex %= game.getPlayers().size();
    game.setNextPlayerMoveIndex(nextPlayerMoveIndex);
    if (checkWinner(game.getBoard(), finalMove)) {
      game.setGameState(GameState.ENDED);
      game.setWinner(currentPlayer);
    } else if (game.getMoves().size() == game.getBoard().getDimension() * game.getBoard().getDimension()) {
      game.setGameState(GameState.DRAW);
    }
  }

  @Override
  public Player getWinner(Game game) {
    return game.getWinner();
  }

  public void undo(Game game) throws EmptyMovesException {
    //Implement Undo functionality.
    if(game.getMoves().isEmpty()) {
      throw new EmptyMovesException(
          ResponseCode.TTT_FAILURE,
          "Undo operation can't be performed as the moves list is empty",
          "Undo operation can't be performed as the moves list is empty"
      );
    }

    int len = game.getMoves().size();
    Move lastMove = game.getMoves().getLast();

    Cell cell = lastMove.getCell();
    int row = cell.getRow();
    int col = cell.getCol();

    game.getBoard().getMatrix().get(row).get(col).setCellState(CellState.EMPTY);

    game.getMoves().remove(len - 1);
  }
}
