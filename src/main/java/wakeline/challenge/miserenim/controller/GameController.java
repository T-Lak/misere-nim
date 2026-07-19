package wakeline.challenge.miserenim.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wakeline.challenge.miserenim.dto.GameCreationResponse;
import wakeline.challenge.miserenim.game.NimGame;
import wakeline.challenge.miserenim.game.Player;
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
      GameCreationResponse response = new GameCreationResponse(
              game.getId(),
              game.getMatches(),
              game.getCurrentPlayer().toString()
      );

      log.info("Game created with id: {}", game.getId());

      return ResponseEntity.ok(response);
   }
}
