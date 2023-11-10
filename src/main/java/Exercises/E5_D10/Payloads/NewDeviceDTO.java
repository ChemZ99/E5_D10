package Exercises.E5_D10.Payloads;

import jakarta.validation.constraints.*;

public record NewDeviceDTO(
        @NotEmpty(message = "type is required")
        @Size(min = 2, max=50, message = "type must have a length beetween 2 and 50")
        String type,
        @NotEmpty(message = "status is required")
        @Size(min = 7, max=15, message = "status must have a length beetween 7 and 15")
        String status) {}