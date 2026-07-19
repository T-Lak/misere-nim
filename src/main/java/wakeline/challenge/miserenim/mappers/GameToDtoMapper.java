package wakeline.challenge.miserenim.mappers;

import wakeline.challenge.miserenim.dto.GameCreationResponse;
import wakeline.challenge.miserenim.dto.GameResponse;
import wakeline.challenge.miserenim.game.NimGame;

import java.util.ArrayList;

public class GameToDtoMapper {

   public static GameResponse toGameResponse(NimGame game) {
      return new GameResponse(
           game.getCurrentPlayer(),
           game.getMatches(),
           game.getStatus(),
           new ArrayList<>(game.getMoveHistory())
      );
   }

   public static GameCreationResponse toGameCreationResponse(NimGame game) {
      return new GameCreationResponse(
              game.getId(),
              game.getMatches(),
              game.getCurrentPlayer().toString(),
              new ArrayList<>(game.getMoveHistory())
      );
   }

}
