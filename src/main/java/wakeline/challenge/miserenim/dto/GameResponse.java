package wakeline.challenge.miserenim.dto;

import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.MoveLog;
import wakeline.challenge.miserenim.game.Player;

import java.util.List;

public record GameResponse(
   Player currentPlayer,
   int matchesLeft,
   GameStatus status,
   List<MoveLog> moveHistory
) {}
