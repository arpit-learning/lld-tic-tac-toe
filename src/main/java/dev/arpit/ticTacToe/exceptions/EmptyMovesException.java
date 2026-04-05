package dev.arpit.ticTacToe.exceptions;

import dev.arpit.ticTacToe.dtos.ResponseCode;

public class EmptyMovesException extends BaseException {
    public EmptyMovesException(ResponseCode code, String message, String displayMessage) {
        super(code, message, displayMessage);
    }
}
