package dev.arpit.ticTacToe.exceptions;

import dev.arpit.ticTacToe.dtos.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends Exception {
  private ResponseCode code;
  private String displayMessage;

  public BaseException(ResponseCode code, String message, String displayMessage) {
    super(message);
    this.code = code;
    this.displayMessage = displayMessage;
  }
}
