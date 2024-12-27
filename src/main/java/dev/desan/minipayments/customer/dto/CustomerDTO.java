package dev.desan.minipayments.customer.dto;

import dev.desan.minipayments.location.dto.LocationDTO;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank(message = "First name is mandatory")
        String firstName,

        @NotBlank(message = "Last name is mandatory")
        String lastName,

        @NotBlank(message = "Name of location is mandatory")
        LocationDTO locationName) {
}
