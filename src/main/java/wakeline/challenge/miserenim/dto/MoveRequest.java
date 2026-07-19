package wakeline.challenge.miserenim.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record MoveRequest(
   @Min(1) @Max(3) int matches
){}
