package integration4.evalebike.controller.technician.dto;


public class TestResponseDTO {
    private String id;
    private String expiryDate;
    private String state;
    private String type;
    private double batteryCapacity;
    private double maxSupport;
    private double enginePowerMax;
    private double enginePowerNominal;
    private double engineTorque;

    // Constructor, getters, and setters
    public TestResponseDTO(String id, String expiryDate, String state, String type, double batteryCapacity,
                           double maxSupport, double enginePowerMax, double enginePowerNominal, double engineTorque) {
        this.id = id;
        this.expiryDate = expiryDate;
        this.state = state;
        this.type = type;
        this.batteryCapacity = batteryCapacity;
        this.maxSupport = maxSupport;
        this.enginePowerMax = enginePowerMax;
        this.enginePowerNominal = enginePowerNominal;
        this.engineTorque = engineTorque;
    }

    public String getId() { return id; }
    public String getExpiryDate() { return expiryDate; }
    public String getState() { return state; }
    public String getType() { return type; }
    public double getBatteryCapacity() { return batteryCapacity; }
    public double getMaxSupport() { return maxSupport; }
    public double getEnginePowerMax() { return enginePowerMax; }
    public double getEnginePowerNominal() { return enginePowerNominal; }
    public double getEngineTorque() { return engineTorque; }



}
