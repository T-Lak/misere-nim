package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.NimGameFactory;
import wakeline.challenge.miserenim.game.Player;
import wakeline.challenge.miserenim.repository.InMemoryGameRepository;

import com.google.common.testing.FakeTicker;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InMemoryGameRepositoryTest {

   private NimGame game;

   @Autowired
   private InMemoryGameRepository repository;

   @BeforeEach
   void setup() {
      this.game = NimGameFactory.create(5, Player.HUMAN, "random");
   }

   @Test
   void testGameCreationShouldStoreGameInCache() {
      this.repository.save(this.game);

      assertNotNull(this.repository.findById(this.game.getId()));
   }

   @Test
   void testShouldCleanCacheAfterGameExpired() {
      NimGame game1 = NimGameFactory.create(3, Player.COMPUTER, "random");
      NimGame game2 = NimGameFactory.create(4, Player.HUMAN, "random");
      NimGame game3 = NimGameFactory.create(10, Player.COMPUTER, "random");

      FakeTicker ticker = new FakeTicker();
      InMemoryGameRepository repository = new InMemoryGameRepository(ticker::read);

      repository.save(this.game);
      repository.save(game1);

      ticker.advance(30, TimeUnit.MINUTES);

      repository.save(game2);
      repository.save(game);

      ticker.advance(121, TimeUnit.MINUTES);

      assertTrue(repository.findById(this.game.getId()).isEmpty());
      assertTrue(repository.findById(game1.getId()).isEmpty());
      assertNotNull(repository.findById(game2.getId()));
      assertNotNull(repository.findById(game3.getId()));
   }

}
