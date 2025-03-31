package integration4.evalebike.domain;

import jakarta.persistence.*;
//I dont think we need this anymore.
@Entity
@Table(name = "test_result")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private float batteryVoltage;
    private float batteryCurrent;
    private float batteryCapacity;
    private float batteryTemperature;
    private boolean chargeStatus;
    private int assistanceLevel;
    private float torqueCrank;
    private float wheelSpeed;
    private int cadence;
    private int engine;
    private float enginePower;
    private float wheelPower;
    private float rolTorque;
    private float loadcell;
    private float rol;
    private float horizontalInclinationSensor;
    private float verticalInclinationSensor;
    private int loadPower;
    private boolean statusPlug;

    public TestResult(float batteryVoltage, float batteryCurrent, float batteryCapacity, float batteryTemperature, boolean chargeStatus, int assistanceLevel, float torqueCrank, float wheelSpeed, int cadence, int engine, float enginePower, float wheelPower, float rolTorque, float loadcell, float rol, float horizontalInclinationSensor, float verticalInclinationSensor, int loadPower, boolean statusPlug) {
        this.batteryVoltage = batteryVoltage;
        this.batteryCurrent = batteryCurrent;
        this.batteryCapacity = batteryCapacity;
        this.batteryTemperature = batteryTemperature;
        this.chargeStatus = chargeStatus;
        this.assistanceLevel = assistanceLevel;
        this.torqueCrank = torqueCrank;
        this.wheelSpeed = wheelSpeed;
        this.cadence = cadence;
        this.engine = engine;
        this.enginePower = enginePower;
        this.wheelPower = wheelPower;
        this.rolTorque = rolTorque;
        this.loadcell = loadcell;
        this.rol = rol;
        this.horizontalInclinationSensor = horizontalInclinationSensor;
        this.verticalInclinationSensor = verticalInclinationSensor;
        this.loadPower = loadPower;
        this.statusPlug = statusPlug;
    }

    public TestResult() {

    }

    public float getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public float getBatteryCurrent() {
        return batteryCurrent;
    }

    public void setBatteryCurrent(float batteryCurrent) {
        this.batteryCurrent = batteryCurrent;
    }

    public float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public float getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(float batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public boolean isChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(boolean chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public int getAssistanceLevel() {
        return assistanceLevel;
    }

    public void setAssistanceLevel(int assistanceLevel) {
        this.assistanceLevel = assistanceLevel;
    }

    public float getTorqueCrank() {
        return torqueCrank;
    }

    public void setTorqueCrank(float torqueCrank) {
        this.torqueCrank = torqueCrank;
    }

    public float getWheelSpeed() {
        return wheelSpeed;
    }

    public void setWheelSpeed(float wheelSpeed) {
        this.wheelSpeed = wheelSpeed;
    }

    public int getCadence() {
        return cadence;
    }

    public void setCadence(int cadence) {
        this.cadence = cadence;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(float enginePower) {
        this.enginePower = enginePower;
    }

    public float getWheelPower() {
        return wheelPower;
    }

    public void setWheelPower(float wheelPower) {
        this.wheelPower = wheelPower;
    }

    public float getRolTorque() {
        return rolTorque;
    }

    public void setRolTorque(float rolTorque) {
        this.rolTorque = rolTorque;
    }

    public float getLoadcell() {
        return loadcell;
    }

    public void setLoadcell(float loadcell) {
        this.loadcell = loadcell;
    }

    public float getRol() {
        return rol;
    }

    public void setRol(float rol) {
        this.rol = rol;
    }

    public float getHorizontalInclinationSensor() {
        return horizontalInclinationSensor;
    }

    public void setHorizontalInclinationSensor(float horizontalInclinationSensor) {
        this.horizontalInclinationSensor = horizontalInclinationSensor;
    }

    public float getVerticalInclinationSensor() {
        return verticalInclinationSensor;
    }

    public void setVerticalInclinationSensor(float verticalInclinationSensor) {
        this.verticalInclinationSensor = verticalInclinationSensor;
    }

    public int getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(int loadPower) {
        this.loadPower = loadPower;
    }

    public boolean isStatusPlug() {
        return statusPlug;
    }

    public void setStatusPlug(boolean statusPlug) {
        this.statusPlug = statusPlug;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
