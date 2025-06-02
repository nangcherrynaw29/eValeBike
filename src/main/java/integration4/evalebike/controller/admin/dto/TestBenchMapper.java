package integration4.evalebike.controller.admin.dto;

import integration4.evalebike.controller.admin.dto.response.TestBenchResponseDTO;
import integration4.evalebike.domain.TestBench;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestBenchMapper {

    @Mapping(target = "technicianId", expression = "java(testBench.getTechnician() != null ? testBench.getTechnician().getId() : null)")
    TestBenchResponseDTO toDto(TestBench testBench);
}