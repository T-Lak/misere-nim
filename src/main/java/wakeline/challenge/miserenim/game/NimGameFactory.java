package wakeline.challenge.miserenim.game;

import java.util.UUID;

public class NimGameFactory {

   public static NimGame create(int matches, Player player) {
      String id = UUID.randomUUID().toString();
      return new NimGame(id, matches, GameStatus.IN_PROGRESS, player);
   }

}
