package integration4.evalebike.controller.technician.dto;

public class TestRequestDTO {
    String testType;
    double batteryCapacity;
    double maxSupport;
    double enginePowerMax;
    double enginePowerNominal;
    double engineTorque;

    public TestRequestDTO(String testType,
                          double batteryCapacity,
                          double maxSupport,
                          double enginePowerMax,
                          double enginePowerNominal,
                          double engineTorque
                          ) {
        this.batteryCapacity = batteryCapacity;
        this.enginePowerMax = enginePowerMax;
        this.enginePowerNominal = enginePowerNominal;
        this.engineTorque = engineTorque;
        this.maxSupport = maxSupport;
        this.testType = testType;
    }

    public double getBatteryCapacity() {
        return batteryCapacity;
    }

    public double getEnginePowerMax() {
        return enginePowerMax;
    }

    public double getEnginePowerNominal() {
        return enginePowerNominal;
    }

    public double getEngineTorque() {
        return engineTorque;
    }

    public double getMaxSupport() {
        return maxSupport;
    }

    public String getTestType() {
        return testType;
    }
}
