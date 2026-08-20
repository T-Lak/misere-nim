package challenge.miserenim.dto;

import challenge.miserenim.game.MoveLog;
import challenge.miserenim.strategy.StrategyType;

import java.util.List;

public record GameCreationResponse(
   String id,
   int initialMatches,
   String playerToStart,
   StrategyType computerStrategy,
   List<MoveLog> moveHistory
) {}
