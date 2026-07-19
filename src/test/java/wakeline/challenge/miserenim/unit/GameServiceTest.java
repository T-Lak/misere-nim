package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wakeline.challenge.miserenim.exception.GameNotFoundException;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.Player;
import wakeline.challenge.miserenim.service.GameService;

import static org.junit.Assert.*;

@SpringBootTest
public class GameServiceTest {

   @Autowired
   private GameService gameService;

   @Test
   void testShouldCreateGameWithValidParams() {
      NimGame game = gameService.createNewGame(5, Player.HUMAN, "random");

      assertEquals(5, game.getMatches());
      assertEquals(Player.HUMAN, game.getCurrentPlayer());
      assertNotNull(game.getId());
   }

   @Test
   void testExceptionOnGameCreationWithInsufficientNumberOfMatches() {
      assertThrows(InvalidMoveException.class, () -> gameService.createNewGame(
              1,
              Player.HUMAN,
              "random"
      ));
   }

   @Test
   void testShouldDecreaseMatchesCountWhenComputerStarts() {
      NimGame game = gameService.createNewGame(5, Player.COMPUTER, "stub");

      assertEquals(4, game.getMatches());
   }

   @Test
   void testMakeHumanMove() {
      NimGame game = gameService.createNewGame(5, Player.HUMAN, "stub");

      gameService.processHumanMove(game.getId(), 3);
      gameService.processHumanMove(game.getId(), 1);

      assertEquals(0, game.getMatches());
      assertSame(GameStatus.FINISHED, game.getStatus());
   }

   @Test
   void testGameNotFoundExceptionOnNonExistingId() {
      assertThrows(GameNotFoundException.class, () -> gameService.processHumanMove("1", 3));
   }

}
