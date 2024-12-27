package dev.desan.minipayments.location.dto;

import jakarta.validation.constraints.NotBlank;

public record LocationDTO(
        @NotBlank(message = "Location name cannot be empty")
        String name) {
}
