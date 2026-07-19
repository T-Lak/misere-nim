package wakeline.challenge.miserenim.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wakeline.challenge.miserenim.dto.GameCreationResponse;
import wakeline.challenge.miserenim.dto.GameResponse;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.Player;
import wakeline.challenge.miserenim.mappers.GameToDtoMapper;
import wakeline.challenge.miserenim.service.GameService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/misere-nim")
@Slf4j
public class GameController {

   private final GameService gameService;

   @PostMapping("/create")
   public ResponseEntity<GameCreationResponse> createGame(
           @RequestParam int matches,
           @RequestParam String player,
           @RequestParam String strategyType
   ) {
      log.info("Game creation request with {} matches and Player: {}", matches, player);

      NimGame game = this.gameService.createNewGame(matches, Player.fromString(player.toLowerCase()), strategyType);
      GameCreationResponse response = GameToDtoMapper.toGameCreationResponse(game);
      log.info("Game created with id: {}", game.getId());

      return ResponseEntity.ok(response);
   }

   @PostMapping("/{gameId}/move")
   public ResponseEntity<GameResponse> makeMove(
           @PathVariable String gameId,
           @RequestParam int matches
   ) {
      log.info("Request to remove {} matches for game with id {}", matches, gameId);

      NimGame game = this.gameService.processHumanMove(gameId, matches);
      GameResponse response = GameToDtoMapper.toGameResponse(game);

      log.info("Sending game response object for game with id: {}", game.getId());

      return ResponseEntity.ok(response);
   }

   @GetMapping("/{gameId}")
   public ResponseEntity<GameResponse> getGameState(@PathVariable String gameId) {
      log.info("Request state for game with id: {}", gameId);

      NimGame game = this.gameService.getGame(gameId);
      GameResponse response = GameToDtoMapper.toGameResponse(game);

      return ResponseEntity.ok(response);
   }
}
