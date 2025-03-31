package integration4.evalebike.controller.testBench.dto;

public class TestRequestDTO {
    String testType;
    double batteryCapacity;
    double maxSupport;
    double enginePowerMax;
    double enginePowerNominal;
    double engineTorque;

    public TestRequestDTO(String testType, float accuCapacity, float maxSupport, float maxEnginePower, float nominalEnginePower, float engineTorque) {
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
