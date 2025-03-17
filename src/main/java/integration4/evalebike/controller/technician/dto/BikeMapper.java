package integration4.evalebike.controller.technician.dto;

import integration4.evalebike.domain.Bike;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeMapper {
    BikeDto toBikeDto(Bike bike);
}
