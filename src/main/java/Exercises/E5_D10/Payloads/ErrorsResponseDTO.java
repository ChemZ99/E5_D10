package Exercises.E5_D10.Payloads;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}