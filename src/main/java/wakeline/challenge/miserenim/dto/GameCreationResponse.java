package wakeline.challenge.miserenim.dto;

import wakeline.challenge.miserenim.game.MoveLog;
import wakeline.challenge.miserenim.strategy.StrategyType;

import java.util.List;

public record GameCreationResponse(
   String id,
   int initialMatches,
   String PlayerToStart,
   StrategyType computerStrategy,
   List<MoveLog> moveHistory
) {}
