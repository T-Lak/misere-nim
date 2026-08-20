package challenge.miserenim.dto;

import challenge.miserenim.game.GameStatus;
import challenge.miserenim.game.MoveLog;
import challenge.miserenim.game.Player;
import challenge.miserenim.strategy.StrategyType;

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
