package challenge.miserenim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import challenge.miserenim.exception.GameNotFoundException;
import challenge.miserenim.exception.InvalidMoveException;
import challenge.miserenim.game.NimGame;
import challenge.miserenim.game.NimGameFactory;
import challenge.miserenim.game.Player;
import challenge.miserenim.repository.InMemoryGameRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

   private final InMemoryGameRepository inMemoryGameRepository;

   public NimGame createNewGame(int matches, Player player, String strategyType) {
      if (matches < 2) {
         throw new InvalidMoveException("Initial number of matches must be greater or equal to 2");
      }

      NimGame game = NimGameFactory.create(matches, player, strategyType);

      if (player == Player.COMPUTER) {
         game.makeComputerMove();
         log.debug("Computer starts game. New match count: {}", game.getMatches());
      }

      inMemoryGameRepository.save(game);

      return game;
   }

   public NimGame processHumanMove(String gameId, int matches) {
      NimGame game = inMemoryGameRepository.findById(gameId)
              .orElseThrow(() -> new GameNotFoundException("Game with ID " + gameId + " not found"));

      game.makeHumanMove(matches);

      if (!game.isGameOver()) {
         game.makeComputerMove();
         log.debug("Computer move completed. New match count: {}", game.getMatches());
      } else {
         log.info("Game {} finished. Winner: {}", gameId, game.getWinner());
      }

      inMemoryGameRepository.save(game);

      return game;
   }

   public NimGame getGame(String gameId) {
      return inMemoryGameRepository.findById(gameId)
              .orElseThrow(() -> new GameNotFoundException("Game with ID " + gameId + " not found"));
   }

}
