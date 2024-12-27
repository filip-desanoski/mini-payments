package dev.desan.minipayments.location.service;

import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.mapper.LocationMapper;
import dev.desan.minipayments.location.model.Location;
import dev.desan.minipayments.location.repository.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = locationMapper.dtoToEntity(locationDTO);
        location = locationRepository.save(location);
        return locationMapper.entityToDto(location);
    }

    public LocationDTO getLocationById(UUID uuid) {
        Optional<Location> location = locationRepository.findById(uuid);
        return location.map(locationMapper::entityToDto).orElse(null);
    }

    public LocationDTO getLocationByName(String name) {
        Optional<Location> location = locationRepository.getLocationByName(name);
        return location.map(locationMapper::entityToDto).orElse(null);
    }

    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        Page<Location> locationsPage = locationRepository.findAll(pageable);
        return locationsPage.map(locationMapper::entityToDto);
    }

    public LocationDTO updateLocation(UUID uuid, LocationDTO locationDTO) {
        Optional<Location> existingLocation = locationRepository.findById(uuid);
        if (existingLocation.isPresent()) {
            Location location = existingLocation.get();
            location.setName(locationDTO.name());
            location = locationRepository.save(location);
            return locationMapper.entityToDto(location);
        }
        return null;
    }

    public void deleteLocation(UUID uuid) {
        locationRepository.deleteById(uuid);
    }
}
