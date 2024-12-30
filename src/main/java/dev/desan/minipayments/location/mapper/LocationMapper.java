package dev.desan.minipayments.location.mapper;

import dev.desan.minipayments.infrastructure.mapper.GeneralMapper;
import dev.desan.minipayments.location.dto.LocationDTO;
import dev.desan.minipayments.location.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GeneralMapper<LocationDTO, Location> {

    @Override
    @Mapping(target = "city", source = "city")
    LocationDTO entityToDto(Location location);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "city", source = "city")
    Location dtoToEntity(LocationDTO locationDTO);
}
