package wakeline.challenge.miserenim.dto;

import wakeline.challenge.miserenim.game.MoveLog;

import java.util.List;

public record GameCreationResponse(
   String id,
   int initialMatches,
   String PlayerToStart,
   List<MoveLog> moveHistory
) {}
