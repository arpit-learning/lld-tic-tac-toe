package dev.arpit.ticTacToe.exceptions;

import dev.arpit.ticTacToe.dtos.ResponseCode;

public class InvalidMoveException extends BaseException {
    public InvalidMoveException(ResponseCode code, String message, String displayMessage) {
        super(code, message, displayMessage);
    }
}
