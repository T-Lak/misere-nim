package wakeline.challenge.miserenim.game;

import lombok.Getter;

@Getter
public enum Player {
   HUMAN("human"),
   COMPUTER("computer");

   private final String player;

   Player(String player) {
      this.player = player;
   }
}
