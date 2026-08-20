package challenge.miserenim.game;

import lombok.Getter;

@Getter
public enum Player {
   HUMAN("human"),
   COMPUTER("computer");

   private final String player;

   Player(String player) {
      this.player = player;
   }

   public static Player fromString(String text) {
      for (Player p : Player.values()) {
         if (p.player.equalsIgnoreCase(text)) {
            return p;
         }
      }
      throw new IllegalArgumentException("No constant with text " + text + " found");
   }
}
