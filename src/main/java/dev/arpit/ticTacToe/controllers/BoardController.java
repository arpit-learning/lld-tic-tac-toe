package dev.arpit.ticTacToe.controllers;

import org.springframework.stereotype.Controller;

import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.services.IBoardService;

@Controller
public class BoardController implements IBoardController {

  private final IBoardService iBoardService;

  public BoardController (IBoardService iBoardService) {
    this.iBoardService = iBoardService;
  }

  @Override
  public void displayBoard (Board board) {
    iBoardService.displayBoard(board);
  }
}
