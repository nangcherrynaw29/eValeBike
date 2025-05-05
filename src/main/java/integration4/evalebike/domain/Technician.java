package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Technician extends User {
    @OneToMany(mappedBy = "technician")
    private List<TestBench> assignedTestBench = new ArrayList<>();

    public Technician(String name, String email) {
        super(name, email);
    }

    public Technician() {

    }

    public List<TestBench> getAssignedTestBench() {
        return assignedTestBench;
    }

    public void setAssignedTestBench(List<TestBench> assignedTestBench) {
        this.assignedTestBench = assignedTestBench;
    }
}
