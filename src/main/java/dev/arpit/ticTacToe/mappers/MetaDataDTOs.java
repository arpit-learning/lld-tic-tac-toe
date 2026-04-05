package dev.arpit.ticTacToe.mappers;

import dev.arpit.ticTacToe.dtos.MetaDataDto;
import dev.arpit.ticTacToe.exceptions.BaseException;

public class MetaDataDTOs {
  public static MetaDataDto getMetaDataDto(BaseException e) {
    return new MetaDataDto(
        e.getCode(),
        e.getMessage(),
        e.getDisplayMessage()
    );
  }
}
