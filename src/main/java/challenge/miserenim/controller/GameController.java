package challenge.miserenim.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import challenge.miserenim.dto.GameCreationRequest;
import challenge.miserenim.dto.GameCreationResponse;
import challenge.miserenim.dto.GameResponse;
import challenge.miserenim.dto.MoveRequest;
import challenge.miserenim.game.NimGame;
import challenge.miserenim.game.Player;
import challenge.miserenim.mappers.GameToDtoMapper;
import challenge.miserenim.service.GameService;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/misere-nim")
@Slf4j
public class GameController {

   private final GameService gameService;

   @PostMapping("/create")
   public ResponseEntity<GameCreationResponse> createGame(@Valid @RequestBody GameCreationRequest request) {
      log.info("Game creation request with {} matches and Player: {}", request.matches(), request.player());

      NimGame game = this.gameService.createNewGame(
              request.matches(),
              Player.fromString(request.player().toLowerCase()),
              request.strategyType()
      );
      GameCreationResponse response = GameToDtoMapper.toGameCreationResponse(game);
      log.info("Game created with id: {}", game.getId());

      return ResponseEntity.ok(response);
   }

   @PostMapping("/{gameId}/move")
   public ResponseEntity<GameResponse> makeMove(
           @PathVariable String gameId,
           @Valid @RequestBody MoveRequest request
   ) {
      log.info("Request to remove {} matches for game with id {}", request.matches(), gameId);

      NimGame game = this.gameService.processHumanMove(gameId, request.matches());
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
