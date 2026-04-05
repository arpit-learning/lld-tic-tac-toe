package dev.arpit.ticTacToe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaDataDto {
  private ResponseCode code;
  private String message;
  private String displayMessage;
}
