package integration4.evalebike.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Technician extends User {
    @OneToMany(mappedBy = "technician", fetch = FetchType.EAGER)
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