package challenge.miserenim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import challenge.miserenim.dto.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

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

   @ExceptionHandler(IllegalStateException.class)
   public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException exception) {
      return new ResponseEntity<>(
              new ErrorResponse("ILLEGAL_STATE", exception.getMessage()),
              HttpStatus.BAD_REQUEST
      );
   }

   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
      return new ResponseEntity<>(
              new ErrorResponse("BAD_REQUEST", exception.getMessage()),
              HttpStatus.BAD_REQUEST
      );
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();

      String errorMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();

      errors.put("errorCode", "BAD_REQUEST");
      errors.put("message", errorMessage);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorResponse> handleGenericException() {
      return new ResponseEntity<>(
              new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"),
              HttpStatus.NOT_FOUND
      );
   }

}
