package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Technician extends User {
    @OneToMany(mappedBy = "technician")
    private List<TestBench> assignedTestBench = new ArrayList<>();

    public Technician(Integer id, String name, String email, String password, List<TestBench> assignedTestBench) {
        super(id, name, email, password);
        this.assignedTestBench = assignedTestBench;
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
