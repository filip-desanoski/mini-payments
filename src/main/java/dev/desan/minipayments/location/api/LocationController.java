package dev.desan.minipayments.location.api;

import dev.desan.minipayments.infrastructure.EndPoints;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EndPoints.LOCATION)
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocation = locationService.createLocation(locationDTO);
        return createdLocation != null ?
                new ResponseEntity<>(createdLocation, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<LocationDTO> getLocationByUuid(@PathVariable UUID uuid) {
        LocationDTO customerDTO = locationService.getLocationById(uuid);
        return customerDTO != null ?
                new ResponseEntity<>(customerDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/city")
    public ResponseEntity<LocationDTO> getLocationByCity(@Valid @RequestParam String cityName) {
        LocationDTO locationDTO = locationService.getLocationByCity(cityName);
        return locationDTO != null ?
                new ResponseEntity<>(locationDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations(Pageable pageable) {
        List<LocationDTO> locationDTOs = locationService.getAllLocations(pageable).getContent();
        return new ResponseEntity<>(locationDTOs, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable UUID uuid, @Valid @RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocation = locationService.updateLocation(uuid, locationDTO);
        return updatedLocation != null ?
                new ResponseEntity<>(updatedLocation, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID uuid) {
        locationService.deleteLocation(uuid);
        return ResponseEntity.noContent().build();
    }
}
