package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.BikeOwner;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeOwnerMapper {
    BikeOwnerDto toBikeOwnerDto(BikeOwner bikeOwner);
}
