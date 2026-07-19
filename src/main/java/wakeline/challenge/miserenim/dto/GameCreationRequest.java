package wakeline.challenge.miserenim.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record GameCreationRequest(
  @Min(2) int matches,
  @NotBlank String player,
  @NotBlank String strategyType
) {}
