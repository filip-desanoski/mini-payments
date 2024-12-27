package dev.desan.minipayments.location.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationDTO(
        @NotBlank(message = "Name of location is mandatory")
        String locationName) {
}
