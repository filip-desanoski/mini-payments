package dev.desan.minipayments.location.service;

import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.mapper.LocationMapper;
import dev.desan.minipayments.location.model.Location;
import dev.desan.minipayments.location.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        Optional<Location> existingLocation = locationRepository.findLocationByCity(locationDTO.city());
        if (existingLocation.isPresent()) {
            log.error("Location {} already exists", locationDTO.city());
            return null;
        }
        Location location = locationMapper.dtoToEntity(locationDTO);
        location = locationRepository.save(location);
        log.info("Location {} created", locationDTO.city());
        return locationMapper.entityToDto(location);
    }

    public LocationDTO getLocationById(UUID uuid) {
        Optional<Location> location = locationRepository.findById(uuid);
        log.info("Location {} found", uuid);
        return location.map(locationMapper::entityToDto).orElse(null);
    }

    public LocationDTO getLocationByCity(String cityName) {
        Optional<Location> location = locationRepository.findLocationByCity(cityName);
        log.info("Location with name {} found", cityName);
        return location.map(locationMapper::entityToDto).orElse(null);
    }

    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        Page<Location> locationsPage = locationRepository.findAll(pageable);
        log.info("Locations found");
        return locationsPage.map(locationMapper::entityToDto);
    }

    public LocationDTO updateLocation(UUID uuid, LocationDTO locationDTO) {
        Optional<Location> existingLocation = locationRepository.findById(uuid);
        if (existingLocation.isPresent()) {
            Location location = existingLocation.get();
            location.setCity(locationDTO.city());
            location = locationRepository.save(location);
            log.info("Location {} updated", locationDTO.city());
            return locationMapper.entityToDto(location);
        }
        log.error("Location {} not found", uuid);
        return null;
    }

    public void deleteLocation(UUID uuid) {
        locationRepository.deleteById(uuid);
        log.info("Location {} deleted", uuid);
    }
}
