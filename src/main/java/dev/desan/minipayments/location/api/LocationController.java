package dev.desan.minipayments.location.api;

import dev.desan.minipayments.infrastructure.EndPoints;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EndPoints.LOCATION)
@Tag(name = "Location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(
            description = "Create a Location",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        return createdLocation != null ?
                new ResponseEntity<>(createdLocation, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            description = "Get a Location using UUID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<LocationDTO> getLocationByUuid(@PathVariable UUID uuid) {
        LocationDTO customerDTO = locationService.getLocationById(uuid);
        return customerDTO != null ?
                new ResponseEntity<>(customerDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            description = "Get Location using name of city",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/city")
    public ResponseEntity<LocationDTO> getLocationByCity(@Valid @RequestParam String cityName) {
        LocationDTO locationDTO = locationService.getLocationByCity(cityName);
        return locationDTO != null ?
                new ResponseEntity<>(locationDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            description = "Get all Locations",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations(Pageable pageable) {
        List<LocationDTO> locationDTOs = locationService.getAllLocations(pageable).getContent();
        return new ResponseEntity<>(locationDTOs, HttpStatus.OK);
    }

    @Operation(
            description = "Update a Location using UUID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    @PutMapping("/{uuid}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable UUID uuid, @Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(uuid, locationDTO);
        return updatedLocation != null ?
                new ResponseEntity<>(updatedLocation, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            description = "Delete a Location using UUID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{uuid}")
    public ResponseEntity<LocationDTO> deleteLocation(@PathVariable UUID uuid) {
        locationService.deleteLocation(uuid);
        return ResponseEntity.noContent().build();
    }
}
