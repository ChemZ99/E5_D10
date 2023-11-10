package Exercises.E5_D10.Payloads;

import jakarta.validation.constraints.*;

public record NewUserDTO(
        @NotEmpty(message = "username is required")
        @Size(min = 3, max=50, message = "username must have a length beetween 3 and 50")
        String username,
        @NotEmpty(message = "name is required")
        @Size(min = 3, max=50, message = "name must have a length beetween 3 and 50")
        String name,
        @NotEmpty(message = "surname is required")
        @Size(min = 2, max=50, message = "surname must have a length beetween 2 and 50")
        String surname,
        @NotEmpty(message = "email is required")
        @Email(message = "this email is not valid")
        String email) {}
