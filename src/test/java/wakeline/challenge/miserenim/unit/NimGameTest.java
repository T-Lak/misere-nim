package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
import wakeline.challenge.miserenim.exception.NotYourTurnException;
import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.NimGameFactory;
import wakeline.challenge.miserenim.game.Player;

import static org.junit.jupiter.api.Assertions.*;


public class NimGameTest {

   private NimGame game;

   @BeforeEach
   void setup() {
      this.game = NimGameFactory.create(5, Player.HUMAN, "stub");
   }

   @Test
   void testMoveReducesMatches() {
      game.makeHumanMove(1);

      assertEquals(4, game.getMatches());
   }

   @Test
   void testSwitchesToNextPlayerAfterMove() {
      game.makeHumanMove(1);

      assertEquals(Player.COMPUTER, game.getCurrentPlayer());
   }

   @Test
   void testExceptionOnInvalidMove() {
      assertThrows(InvalidMoveException.class, () -> game.makeHumanMove(4));
      assertThrows(InvalidMoveException.class, () -> game.makeHumanMove(0));
   }

   @Test
   void testExceptionOnWrongPlayer() {
      game.makeHumanMove(1);

      assertThrows(NotYourTurnException.class, () -> game.makeHumanMove(1));
   }

   @Test
   void testExceptionOnIllegalStateChange() {
      game.makeHumanMove(3);
      game.makeComputerMove();
      game.makeHumanMove(1);

      assertThrows(IllegalStateException.class, () -> game.setGameStatus(GameStatus.IN_PROGRESS));
   }

   @Test
   void testExceptionOnGetWinnerWhileGameInProgress() {
      game.makeHumanMove(2);
      game.makeComputerMove();

      assertThrows(IllegalStateException.class, () -> game.getWinner());
   }

   @Test
   void testFullGamePlay() {
      game.makeHumanMove(1);
      game.makeComputerMove();

      assertEquals(Player.HUMAN, game.getCurrentPlayer());
      assertEquals(3, game.getMatches());

      game.makeHumanMove(2);
      game.makeComputerMove();
      assertEquals(0, game.getMatches());
      assertTrue(game.isGameOver());
      assertEquals(Player.HUMAN, game.getWinner());
   }
}
