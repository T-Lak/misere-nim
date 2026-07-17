package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wakeline.challenge.miserenim.exception.GameOverException;
import wakeline.challenge.miserenim.exception.InsufficientMatchesException;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
import wakeline.challenge.miserenim.exception.NotYourTurnException;
import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.Player;

import static org.junit.jupiter.api.Assertions.*;


public class NimGameTest {

   private NimGame game;

   @BeforeEach
   void setup() {
      game = new NimGame("test-id", 5, GameStatus.IN_PROGRESS, Player.HUMAN);
   }

   @Test
   void testMoveReducesMatches() {
      game.makeMove(1, Player.HUMAN);

      assertEquals(4, game.getMatches());
   }

   @Test
   void testSwitchesToNextPlayerAfterMove() {
      game.makeMove(1, Player.HUMAN);

      assertEquals(Player.COMPUTER, game.getCurrentPlayer());
   }

   @Test
   void testExceptionOnInvalidMove() {
      assertThrows(InvalidMoveException.class, () -> game.makeMove(4, Player.HUMAN));
      assertThrows(InvalidMoveException.class, () -> game.makeMove(0, Player.HUMAN));
   }

   @Test
   void testExceptionOnWrongPlayer() {
      assertThrows(NotYourTurnException.class, () -> game.makeMove(1, Player.COMPUTER));
   }

   @Test
   void testExceptionOnInsufficientMatches() {
      game.makeMove(3, Player.HUMAN);

      assertThrows(InsufficientMatchesException.class, () -> game.makeMove(3, Player.COMPUTER));
   }

   @Test
   void testExceptionOnGameOver() {
      game.makeMove(3, Player.HUMAN);
      game.makeMove(1, Player.COMPUTER);

      assertThrows(GameOverException.class, () -> game.makeMove(1, Player.HUMAN));
   }

   @Test
   void testFullGamePlay() {
      game.makeMove(1, Player.HUMAN);
      assertEquals(Player.COMPUTER, game.getCurrentPlayer());

      game.makeMove(1, Player.COMPUTER);
      assertEquals(Player.HUMAN, game.getCurrentPlayer());
      assertEquals(3, game.getMatches());

      game.makeMove(2, Player.HUMAN);
      assertEquals(1, game.getMatches());
      assertTrue(game.isGameOver());
   }
}
