package challenge.miserenim.game;

import challenge.miserenim.strategy.StrategyType;

import java.util.ArrayList;
import java.util.UUID;

public class NimGameFactory {

   public static NimGame create(int matches, Player player, String strategyName) {
      String id = UUID.randomUUID().toString();

      StrategyType strategyType = StrategyType.valueOf(strategyName.toUpperCase());

      return new NimGame(id, matches, GameStatus.IN_PROGRESS, player, strategyType, new ArrayList<>());
   }

}
