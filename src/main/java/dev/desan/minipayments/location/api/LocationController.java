package dev.desan.minipayments.location.api;

import dev.desan.minipayments.customer.dto.CustomerDTO;
import dev.desan.minipayments.infrastructure.EndPoints;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable UUID uuid) {
        LocationDTO locationDTO = locationService.getLocationById(uuid);
        return locationDTO != null ?
                new ResponseEntity<>(locationDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/location-name")
    public ResponseEntity<LocationDTO> getLocationByName(@RequestParam String locationName) {
        LocationDTO locationDTO = locationService.getLocationByName(locationName);
        return locationDTO != null ?
                new ResponseEntity<>(locationDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Page<LocationDTO>> getAllLocations(Pageable pageable) {
        Page<LocationDTO> locationDTOS = locationService.getAllLocations(pageable);
        return new ResponseEntity<>(locationDTOS, HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
