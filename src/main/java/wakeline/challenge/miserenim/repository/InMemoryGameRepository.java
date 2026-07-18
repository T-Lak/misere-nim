package wakeline.challenge.miserenim.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.stereotype.Repository;
import wakeline.challenge.miserenim.game.NimGame;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Repository to store game instances in a concurrent cache.
 * Games are automatically removed 60 minutes after their last access
 * to optimize memory usage and discard idle games.
 */
@Repository
public class InMemoryGameRepository {

   private final Cache<String, NimGame> gameCache;

   public InMemoryGameRepository() {
      this(Ticker.systemTicker());
   }

   public InMemoryGameRepository(Ticker ticker) {
      this.gameCache = Caffeine.newBuilder()
              .expireAfterAccess(60, TimeUnit.MINUTES)
              .ticker(ticker)
              .build();
   }

   public void save(NimGame game) {
      gameCache.put(game.getId(), game);
   }

   public Optional<NimGame> findById(String gameId) {
      return Optional.ofNullable(gameCache.getIfPresent(gameId));
   }

}
