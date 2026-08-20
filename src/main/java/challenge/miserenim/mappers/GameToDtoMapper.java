package challenge.miserenim.mappers;

import challenge.miserenim.dto.GameCreationResponse;
import challenge.miserenim.dto.GameResponse;
import challenge.miserenim.game.NimGame;

import java.util.ArrayList;

public class GameToDtoMapper {

   public static GameResponse toGameResponse(NimGame game) {
      return new GameResponse(
           game.getId(),
           game.getCurrentPlayer(),
           game.getStrategyType(),
           game.getMatches(),
           game.getStatus(),
           new ArrayList<>(game.getMoveHistory()),
           game.isGameOver() ? game.getWinner() : null
      );
   }

   public static GameCreationResponse toGameCreationResponse(NimGame game) {
      return new GameCreationResponse(
              game.getId(),
              game.getMatches(),
              game.getCurrentPlayer().toString(),
              game.getStrategyType(),
              new ArrayList<>(game.getMoveHistory())
      );
   }

}
