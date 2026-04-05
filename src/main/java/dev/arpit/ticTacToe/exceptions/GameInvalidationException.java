package dev.arpit.ticTacToe.exceptions;

import dev.arpit.ticTacToe.dtos.ResponseCode;

public class GameInvalidationException extends BaseException {
    public GameInvalidationException(ResponseCode code, String message, String displayMessage) {
        super(code, message, displayMessage);
    }
}
