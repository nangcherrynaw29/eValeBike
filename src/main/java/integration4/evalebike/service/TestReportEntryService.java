package integration4.evalebike.service;

import integration4.evalebike.controller.technician.dto.NormalizedTestReportEntryDTO;
import integration4.evalebike.domain.TestReportEntry;
import integration4.evalebike.repository.TestReportEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ToDoubleFunction;

@Service
public class TestReportEntryService {
    private final TestReportEntryRepository testReportEntryRepository;

    public TestReportEntryService(TestReportEntryRepository testReportEntryRepository) {
        this.testReportEntryRepository = testReportEntryRepository;
    }

    public List<TestReportEntry> getEntriesByReportId(String reportId) {
        return testReportEntryRepository.findTestReportEntriesByTestReportId(reportId);
    }

    public List<NormalizedTestReportEntryDTO> getNormalizedEntriesByReportId(String testId) {
        List<TestReportEntry> entries = getEntriesByReportId(testId);
        if (entries.isEmpty()) return List.of();

        return entries.stream()
                .map(e -> new NormalizedTestReportEntryDTO(
                        e.getTimestamp(),
                        normalize(e.getBatteryVoltage(), entries, TestReportEntry::getBatteryVoltage),
                        normalize(e.getBatteryCurrent(), entries, TestReportEntry::getBatteryCurrent),
                        normalize(e.getBatteryCapacity(), entries, TestReportEntry::getBatteryCapacity),
                        normalize(e.getBatteryTemperatureCelsius(), entries, TestReportEntry::getBatteryTemperatureCelsius),
                        normalize(e.getChargeStatus(), entries, TestReportEntry::getChargeStatus),
                        normalize(e.getAssistanceLevel(), entries, TestReportEntry::getAssistanceLevel),
                        normalize(e.getTorqueCrankNm(), entries, TestReportEntry::getTorqueCrankNm),
                        normalize(e.getBikeWheelSpeedKmh(), entries, TestReportEntry::getBikeWheelSpeedKmh),
                        normalize(e.getCadanceRpm(), entries, TestReportEntry::getCadanceRpm),
                        normalize(e.getEngineRpm(), entries, TestReportEntry::getEngineRpm),
                        normalize(e.getEnginePowerWatt(), entries, TestReportEntry::getEnginePowerWatt),
                        normalize(e.getWheelPowerWatt(), entries, TestReportEntry::getWheelPowerWatt),
                        normalize(e.getRollTorque(), entries, TestReportEntry::getRollTorque),
                        normalize(e.getLoadcellN(), entries, TestReportEntry::getLoadcellN),
                        normalize(e.getRolHz(), entries, TestReportEntry::getRolHz),
                        normalize(e.getHorizontalInclination(), entries, TestReportEntry::getHorizontalInclination),
                        normalize(e.getVerticalInclination(), entries, TestReportEntry::getVerticalInclination),
                        normalize(e.getLoadPower(), entries, TestReportEntry::getLoadPower),
                        e.isStatusPlug() ? 1.0 : 0.0
                ))
                .toList();
    }

    private double normalize(double value, List<TestReportEntry> entries, ToDoubleFunction<TestReportEntry> extractor) {
        double min = entries.stream().mapToDouble(extractor).min().orElse(0);
        double max = entries.stream().mapToDouble(extractor).max().orElse(1);
        if (max - min == 0) return 0.0;
        return 2 * (value - min) / (max - min) - 1;
    }



}
