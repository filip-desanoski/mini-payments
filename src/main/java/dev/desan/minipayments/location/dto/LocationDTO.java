package dev.desan.minipayments.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LocationDTO(

        @NotBlank(message = "City name is mandatory")
        @Size(max = 50, message = "City name must not exceed 50 characters")
        String city) {
}
