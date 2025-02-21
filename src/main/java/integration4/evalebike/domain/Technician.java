package integration4.evalebike.domain;

public class Technician extends User {
    private TestBench assignedTestBench;

    public Technician(Integer id, String name, String email, String password, TestBench testBench) {
        super(id, name, email, password);
        this.assignedTestBench = testBench;
    }

    public TestBench getAssignedTestBench() {
        return assignedTestBench;
    }

    public void setAssignedTestBench(TestBench assignedTestBench) {
        this.assignedTestBench = assignedTestBench;
    }
}
