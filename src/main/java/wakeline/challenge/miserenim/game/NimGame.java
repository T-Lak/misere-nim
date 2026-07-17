package wakeline.challenge.miserenim.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import wakeline.challenge.miserenim.exception.GameOverException;
import wakeline.challenge.miserenim.exception.InsufficientMatchesException;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
import wakeline.challenge.miserenim.exception.NotYourTurnException;

@Component
@AllArgsConstructor
@Getter
public class NimGame {

   private final String id;
   private int matches;
   private GameStatus status;
   private Player currentPlayer;

   public void makeMove(int matches, Player player) {
      if (isGameOver()) {
         throw new GameOverException("The game is already over.");
      }
      if (!this.currentPlayer.equals(player)) {
         throw new NotYourTurnException("It is not this player's turn.");
      }
      if (!isValidNumberOfMatches(matches)) {
         throw new InvalidMoveException("You must take between 1 and 3 matches.");
      }
      if (!remainingMatchesSufficient(matches)) {
         throw new InsufficientMatchesException("Not enough matches in the heap.");
      }

      this.matches -= matches;

      this.currentPlayer = (this.currentPlayer.equals(Player.HUMAN)) ? Player.COMPUTER : Player.HUMAN;
   }

   public boolean isGameOver() {
      return this.matches <= 1;
   }

   private boolean isValidNumberOfMatches(int matches) {
      return matches >= 1 && matches <= 3;
   }

   private boolean remainingMatchesSufficient(int matches) {
      return this.matches >= matches;
   }
}
