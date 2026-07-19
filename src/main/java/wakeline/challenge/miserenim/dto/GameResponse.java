package wakeline.challenge.miserenim.dto;

import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.MoveLog;
import wakeline.challenge.miserenim.game.Player;
import wakeline.challenge.miserenim.strategy.StrategyType;

import java.util.List;

public record GameResponse(
   String id,
   Player currentPlayer,
   StrategyType computerStrategy,
   int matchesLeft,
   GameStatus status,
   List<MoveLog> moveHistory,
   Player winner
) {}
