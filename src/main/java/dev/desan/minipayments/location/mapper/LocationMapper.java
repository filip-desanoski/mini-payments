package dev.desan.minipayments.location.mapper;

import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GeneralMapper<LocationDTO, Location> {

    @Override
    LocationDTO entityToDto(Location location);

    @Override
    Location dtoToEntity(LocationDTO locationDTO);
}
