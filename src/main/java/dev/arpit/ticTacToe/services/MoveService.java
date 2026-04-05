package dev.arpit.ticTacToe.services;

import dev.arpit.ticTacToe.models.Board;
import dev.arpit.ticTacToe.models.Move;
import dev.arpit.ticTacToe.models.Player;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MoveService implements IMoveService {
  @Override
  public Move makeMove(Board board, Player player) {
    Scanner scanner = new Scanner(System.in);

    //Ask the Player to provide the index to make a move.
    System.out.println("Please tell the row index to make a move");
    int rowNumber = scanner.nextInt();

    System.out.println("Please tell the col index to make a move");
    int colNumber = scanner.nextInt();

    return new Move(player, board.getMatrix().get(rowNumber).get(colNumber));
  }
}
