package io.erickdev.api_players.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;

@ControllerAdvice
public class PlayerExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> playerNotFoundHandler (PlayerNotFoundException exception, HttpServletRequest req) {
        PlayerErrorResponse playerErrorResponse = new PlayerErrorResponse(ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(), req.getRequestURI(), exception.getMessage() );
        return new ResponseEntity<>(playerErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> playerSuccessHandler(PlayerSuccessException exception, HttpServletRequest req) {
        PlayerErrorResponse playerErrorResponse = new PlayerErrorResponse(ZonedDateTime.now(),
                HttpStatus.OK.value(), req.getRequestURI(), exception.getMessage() );
        return new ResponseEntity<>(playerErrorResponse, HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<PlayerErrorResponse> genericHandler (Exception exception, HttpServletRequest req) {
        PlayerErrorResponse playerErrorResponse = new PlayerErrorResponse(ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), exception.getMessage() );
        return new ResponseEntity<>(playerErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
