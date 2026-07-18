package wakeline.challenge.miserenim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wakeline.challenge.miserenim.exception.InvalidMoveException;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.NimGameFactory;
import wakeline.challenge.miserenim.game.Player;
import wakeline.challenge.miserenim.repository.InMemoryGameRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

   private final InMemoryGameRepository inMemoryGameRepository;

   public NimGame createNewGame(int matches, Player player) {
      if (matches <= 2) {
         throw new InvalidMoveException("Initial number of matches must be greater or equal to 2");
      }

      NimGame game = NimGameFactory.create(matches, player);

      inMemoryGameRepository.save(game);
      log.info("Created game with id {}", game.getId());

      return game;
   }

}
