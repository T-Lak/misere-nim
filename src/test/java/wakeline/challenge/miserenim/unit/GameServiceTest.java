package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
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
      NimGame game = gameService.createNewGame(5, Player.HUMAN);

      assertEquals(5, game.getMatches());
      assertEquals(Player.HUMAN, game.getCurrentPlayer());
      assertNotNull(game.getId());
   }

   @Test
   void shouldExceptionOnGameCreationWithInsufficientNumberOfMatches() {
      assertThrows(InvalidMoveException.class, () -> gameService.createNewGame(1, Player.HUMAN));
   }
}
