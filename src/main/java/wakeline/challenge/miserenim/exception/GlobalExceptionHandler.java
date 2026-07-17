package wakeline.challenge.miserenim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wakeline.challenge.miserenim.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(GameNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleGameNotFound(GameNotFoundException exception) {
      return new ResponseEntity<>(
           new ErrorResponse("NOT_FOUND", exception.getMessage()),
           HttpStatus.NOT_FOUND
      );
   }

   @ExceptionHandler(InvalidMoveException.class)
   public ResponseEntity<ErrorResponse> handleInvalidMove(InvalidMoveException exception) {
      return new ResponseEntity<>(
              new ErrorResponse("INVALID_MOVE", exception.getMessage()),
              HttpStatus.BAD_REQUEST
      );
   }

   @ExceptionHandler(NotYourTurnException.class)
   public ResponseEntity<ErrorResponse> handleNotYourTurn(NotYourTurnException exception) {
      return new ResponseEntity<>(
              new ErrorResponse("NOT_YOUR_TURN", exception.getMessage()),
              HttpStatus.BAD_REQUEST
      );
   }

   @ExceptionHandler(GameOverException.class)
   public ResponseEntity<ErrorResponse> handleGameOVer(GameOverException exception) {
      return new ResponseEntity<>(
              new ErrorResponse("GAME_OVER", exception.getMessage()),
              HttpStatus.BAD_REQUEST
      );
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
      return new ResponseEntity<>(
              new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"),
              HttpStatus.NOT_FOUND
      );
   }

}
