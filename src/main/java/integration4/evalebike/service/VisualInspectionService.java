package integration4.evalebike.service;

import integration4.evalebike.domain.VisualInspection;
import integration4.evalebike.repository.VisualInspectionRepository;
import org.springframework.stereotype.Service;

@Service
public class VisualInspectionService {
    private final VisualInspectionRepository visualInspectionRepository;

    public VisualInspectionService(VisualInspectionRepository visualInspectionRepository) {
        this.visualInspectionRepository = visualInspectionRepository;
    }

    public void saveInspection(VisualInspection inspection) {
        visualInspectionRepository.save(inspection);
    }

    public VisualInspection getInspectionByReportID(String id) {
       return visualInspectionRepository.findVisualInspectionByTestReportId(id);
    }
}
