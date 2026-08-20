package challenge.miserenim.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import challenge.miserenim.exception.GameOverException;
import challenge.miserenim.exception.InsufficientMatchesException;
import challenge.miserenim.exception.InvalidMoveException;
import challenge.miserenim.exception.NotYourTurnException;
import challenge.miserenim.strategy.StrategyType;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class NimGame {

   private final String id;
   private int matches;
   private GameStatus status;
   private Player currentPlayer;
   private StrategyType strategyType;
   private List<MoveLog> moveHistory;

   public void makeHumanMove(int matches) {
     this.validateMove(matches, Player.HUMAN);
     this.applyMove(matches);

     this.moveHistory.add(new MoveLog(Player.HUMAN, matches, this.matches));

      if (isGameOver()) {
         this.status = GameStatus.FINISHED;
      } else {
         this.currentPlayer = Player.COMPUTER;
      }
   }

   public void makeComputerMove() {
      int move = this.strategyType.getStrategy().calculateMove(this.matches);
      applyMove(move);

      this.moveHistory.add(new MoveLog(Player.COMPUTER, move, this.matches));

      if (isGameOver()) {
         this.status = GameStatus.FINISHED;
      } else {
         this.currentPlayer = Player.HUMAN;
      }
   }

   public boolean isGameOver() {
      return this.matches == 0;
   }

   public void setGameStatus(GameStatus other) {
      if (this.status == GameStatus.FINISHED && other == GameStatus.IN_PROGRESS) {
         throw new IllegalStateException("Cannot restart a finished game.");
      }

      this.status = other;
   }

   public Player getWinner() {
      if (this.status != GameStatus.FINISHED) {
         throw new IllegalStateException("Game is still in progress.");
      }

      Player loser = this.moveHistory.getLast().player();
      return (loser == Player.HUMAN) ? Player.COMPUTER : Player.HUMAN;
   }

   private void applyMove(int matches) {
      this.matches -= matches;
   }

   private void validateMove(int matches, Player player) {
      if (isGameOver()) throw new GameOverException("Game over.");
      if (this.currentPlayer != player) throw new NotYourTurnException("Not your turn.");
      if (!this.isValidNumberOfMatches(matches)) {
         throw new InvalidMoveException("You must take between 1 and 3 matches.");
      }
      if (!this.remainingMatchesSufficient(matches)) {
         throw new InsufficientMatchesException(
              String.format("Request to remove %d matches, but only %d matches left.", matches, this.matches)
         );
      }
   }

   private boolean isValidNumberOfMatches(int matches) {
      return matches >= 1 && matches <= Math.min(3, this.matches);
   }

   private boolean remainingMatchesSufficient(int matches) {
      return this.matches >= matches;
   }
}
