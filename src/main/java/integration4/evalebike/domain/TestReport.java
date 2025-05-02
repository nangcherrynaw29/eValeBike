package integration4.evalebike.domain;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "test_reports")
public class TestReport {

    @Id
    private String id;

    @Column(name = "expiry_date")
    private String expiryDate;

    private String state;

    private String type;

    @Column(name = "battery_capacity")
    private double batteryCapacity;

    @Column(name = "max_support")
    private double maxSupport;

    @Column(name = "engine_power_max")
    private double enginePowerMax;

    @Column(name = "engine_power_nominal")
    private double enginePowerNominal;

    @Column(name = "engine_torque")
    private double engineTorque;

    @OneToMany(mappedBy = "testReport", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<TestReportEntry> reportEntries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_QR", referencedColumnName = "bikeqr")
    private Bike bike;

    @Column(name = "technician_username")
    private  String technicianName;

    // Default constructor for JPA
    public TestReport() {}

    // Constructor for mapping from DTO


    public TestReport(double batteryCapacity, Bike bike, double enginePowerMax, double enginePowerNominal, double engineTorque, String expiryDate, String id, double maxSupport, List<TestReportEntry> reportEntries, String state, String technicianName, String type) {
        this.batteryCapacity = batteryCapacity;
        this.bike = bike;
        this.enginePowerMax = enginePowerMax;
        this.enginePowerNominal = enginePowerNominal;
        this.engineTorque = engineTorque;
        this.expiryDate = expiryDate;
        this.id = id;
        this.maxSupport = maxSupport;
        this.reportEntries = reportEntries;
        this.state = state;
        this.technicianName = technicianName;
        this.type = type;
    }

    public TestReport(String id, String expiryDate, String state, String type, double batteryCapacity, double maxSupport, double enginePowerMax, double enginePowerNominal, double engineTorque) {
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

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(double batteryCapacity) { this.batteryCapacity = batteryCapacity; }
    public double getMaxSupport() { return maxSupport; }
    public void setMaxSupport(double maxSupport) { this.maxSupport = maxSupport; }
    public double getEnginePowerMax() { return enginePowerMax; }
    public void setEnginePowerMax(double enginePowerMax) { this.enginePowerMax = enginePowerMax; }
    public double getEnginePowerNominal() { return enginePowerNominal; }
    public void setEnginePowerNominal(double enginePowerNominal) { this.enginePowerNominal = enginePowerNominal; }
    public double getEngineTorque() { return engineTorque; }
    public void setEngineTorque(double engineTorque) { this.engineTorque = engineTorque; }
    public List<TestReportEntry>  getReportEntries() { return reportEntries; }
    public void setReportEntries(List<TestReportEntry> reportEntries) { this.reportEntries = reportEntries; }
    public Bike getBike() {return bike;}

    public void setBike(Bike bike) {this.bike = bike;}

    public String getTechnicianName() {return technicianName;}

    public void setTechnicianName(String technianName) {this.technicianName = technianName;}
}