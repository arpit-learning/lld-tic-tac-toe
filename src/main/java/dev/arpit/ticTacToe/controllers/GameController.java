package dev.arpit.ticTacToe.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;

import dev.arpit.ticTacToe.dtos.MetaDataDto;
import dev.arpit.ticTacToe.dtos.ResponseCode;
import dev.arpit.ticTacToe.dtos.ResponseDto;
import dev.arpit.ticTacToe.exceptions.BaseException;
import dev.arpit.ticTacToe.mappers.MetaDataDTOs;
import dev.arpit.ticTacToe.models.Game;
import dev.arpit.ticTacToe.models.Player;
import dev.arpit.ticTacToe.models.constants.GameState;
import dev.arpit.ticTacToe.services.IGameService;

@Controller
public class GameController implements IGameController {

  private final IGameService iGameService;

  public GameController(IGameService iGameService) {
    this.iGameService = iGameService;
  }

  @Override
  public ResponseDto<Game> startGame(int dimension, List<Player> players) {
    ResponseDto<Game> responseDto = new ResponseDto<>();

    try {
      Game game = iGameService.buildGame(dimension, players);
      responseDto.setData(game);
      responseDto.setMeta(
          new MetaDataDto(
              ResponseCode.TTT_SUCCESS,
              "Game started successfully",
              "Game started successfully"
          )
      );

      return responseDto;
    } catch(BaseException e) {
      responseDto.setMeta(MetaDataDTOs.getMetaDataDto(e));
      return responseDto;
    }
  }

  @Override
  public ResponseDto<Void> makeMove(Game game) {
    ResponseDto<Void> responseDto = new ResponseDto<>();
    try {
      iGameService.makeMove(game);
      responseDto.setMeta(
          new MetaDataDto(
              ResponseCode.TTT_SUCCESS,
              "Move made successfully",
              "Move made successfully"
          )
      );

      return responseDto;
    } catch (BaseException e) {
      responseDto.setMeta(MetaDataDTOs.getMetaDataDto(e));
      return responseDto;
    }
  }

  @Override
  public ResponseDto<Player> getWinner(Game game) {
    ResponseDto<Player> responseDto = new ResponseDto<>();
    responseDto.setData(iGameService.getWinner(game));
    return responseDto;
  }

  @Override
  public ResponseDto<Void> undo(Game game) {
    ResponseDto<Void> responseDto = new ResponseDto<>();

    try {
      iGameService.undo(game);
      responseDto.setMeta(
          new MetaDataDto(
              ResponseCode.TTT_SUCCESS,
              "game undo successfully",
              "game undo successfully"
          )
      );

      return responseDto;
    } catch (BaseException e) {
      responseDto.setMeta(MetaDataDTOs.getMetaDataDto(e));
      return responseDto;
    }
  }

  @Override
  public ResponseDto<GameState> getGameState(Game game) {
      ResponseDto<GameState> responseDto = new ResponseDto<>();
      responseDto.setData(game.getGameState());
      return responseDto;
  }
}
