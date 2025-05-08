package integration4.evalebike.domain;

import jakarta.persistence.*;

@Entity
public class VisualInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tires;
    private String bell;
    private String cranks;
    private String electricWiring;
    private String frontFork;
    private String handles;
    private String chainBelt;
    private String pedals;
    private String reflectors;
    private String brakePads;
    private String brakeHandles;
    private String brakeCables;
    private String brakeDiscs;
    private String mudguards;
    private String handleBar;
    private String rearSprocket;
    private String frontSprocket;
    private String rimsSpokes;
    private String rearSuspension;
    private String suspensionFront;
    private String saddle;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_report_id", referencedColumnName = "id", unique = true)
    private TestReport testReport;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTires() {
        return tires;
    }

    public void setTires(String tires) {
        this.tires = tires;
    }

    public String getBell() {
        return bell;
    }

    public void setBell(String bell) {
        this.bell = bell;
    }

    public String getCranks() {
        return cranks;
    }

    public void setCranks(String cranks) {
        this.cranks = cranks;
    }

    public String getElectricWiring() {
        return electricWiring;
    }

    public void setElectricWiring(String electricWiring) {
        this.electricWiring = electricWiring;
    }

    public String getFrontFork() {
        return frontFork;
    }

    public void setFrontFork(String frontFork) {
        this.frontFork = frontFork;
    }

    public String getHandles() {
        return handles;
    }

    public void setHandles(String handles) {
        this.handles = handles;
    }

    public String getChainBelt() {
        return chainBelt;
    }

    public void setChainBelt(String chainBelt) {
        this.chainBelt = chainBelt;
    }

    public String getPedals() {
        return pedals;
    }

    public void setPedals(String pedals) {
        this.pedals = pedals;
    }

    public String getReflectors() {
        return reflectors;
    }

    public void setReflectors(String reflectors) {
        this.reflectors = reflectors;
    }

    public String getBrakePads() {
        return brakePads;
    }

    public void setBrakePads(String brakePads) {
        this.brakePads = brakePads;
    }

    public String getBrakeHandles() {
        return brakeHandles;
    }

    public void setBrakeHandles(String brakeHandles) {
        this.brakeHandles = brakeHandles;
    }

    public String getBrakeCables() {
        return brakeCables;
    }

    public void setBrakeCables(String brakeCables) {
        this.brakeCables = brakeCables;
    }

    public String getBrakeDiscs() {
        return brakeDiscs;
    }

    public void setBrakeDiscs(String brakeDiscs) {
        this.brakeDiscs = brakeDiscs;
    }

    public String getMudguards() {
        return mudguards;
    }

    public void setMudguards(String mudguards) {
        this.mudguards = mudguards;
    }

    public String getHandleBar() {
        return handleBar;
    }

    public void setHandleBar(String handleBar) {
        this.handleBar = handleBar;
    }

    public String getRearSprocket() {
        return rearSprocket;
    }

    public void setRearSprocket(String rearSprocket) {
        this.rearSprocket = rearSprocket;
    }

    public String getFrontSprocket() {
        return frontSprocket;
    }

    public void setFrontSprocket(String frontSprocket) {
        this.frontSprocket = frontSprocket;
    }

    public String getRimsSpokes() {
        return rimsSpokes;
    }

    public void setRimsSpokes(String rimsSpokes) {
        this.rimsSpokes = rimsSpokes;
    }

    public String getRearSuspension() {
        return rearSuspension;
    }

    public void setRearSuspension(String rearSuspension) {
        this.rearSuspension = rearSuspension;
    }

    public String getSuspensionFront() {
        return suspensionFront;
    }

    public void setSuspensionFront(String suspensionFront) {
        this.suspensionFront = suspensionFront;
    }

    public String getSaddle() {
        return saddle;
    }

    public void setSaddle(String saddle) {
        this.saddle = saddle;
    }

    public TestReport getTestReport() {
        return testReport;
    }

    public void setTestReport(TestReport testReport) {
        this.testReport = testReport;
    }
}

