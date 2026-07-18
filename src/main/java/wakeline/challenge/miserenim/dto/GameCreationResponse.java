package wakeline.challenge.miserenim.dto;

public record GameCreationResponse(
   String id,
   int initialMatches,
   String PlayerToStart
) {}
