package dev.arpit.ticTacToe;

import dev.arpit.ticTacToe.controllers.IBoardController;
import dev.arpit.ticTacToe.controllers.IGameController;
import dev.arpit.ticTacToe.dtos.*;
import dev.arpit.ticTacToe.models.Game;
import dev.arpit.ticTacToe.models.constants.GameState;
import dev.arpit.ticTacToe.models.Player;
import dev.arpit.ticTacToe.models.Symbol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TicTacToeApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication app = new SpringApplication(TicTacToeApplication.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    try (ConfigurableApplicationContext ctx = app.run(args)) {
      IGameController iGameController = ctx.getBean(IGameController.class);
      IBoardController iBoardController = ctx.getBean(IBoardController.class);

      int dimension = 3;
      List<Player> players = new ArrayList<>();
      players.add(new Player("Player1", new Symbol('X')));
      players.add(new Player("Player2", new Symbol('O')));

      ResponseDto<Game> gameResponseDto = iGameController.startGame(dimension, players);
      Game game = gameResponseDto.getData();
      // gameController.displayBoard(game);

      while (iGameController.getGameState(game).getData().equals(GameState.IN_PROGRESS)) {
        iBoardController.displayBoard(game.getBoard());
        ResponseDto<Void> moveResponseDto = iGameController.makeMove(game);
        if(!moveResponseDto.getMeta().getCode().equals(ResponseCode.TTT_SUCCESS)) {
          System.out.println(moveResponseDto.getMeta().getDisplayMessage());
        }
      }

      if (iGameController.getGameState(game).getData().equals(GameState.DRAW)) {
        //Game DRAW
        System.out.println("Game has DRAWN");
      } else if (iGameController.getGameState(game).getData().equals(GameState.ENDED)) {
        iBoardController.displayBoard(game.getBoard());
        System.out.println("Winner is : " + iGameController.getWinner(game).getData().getName());
      }
    }
  }
}
