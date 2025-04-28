package integration4.evalebike.controller.admin.dto;

import integration4.evalebike.controller.admin.dto.response.TestBenchResponseDTO;
import integration4.evalebike.domain.TestBench;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestBenchMapper {

    @Mapping(target = "technicianId", source = "technician.id")
    TestBenchResponseDTO toDto(TestBench testBench);
}