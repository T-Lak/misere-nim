package challenge.miserenim.game;

public record MoveLog(
  Player player,
  int matchesRemoved,
  int matchesLeft
) {}
