package integration4.evalebike.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="recent_activity")
public class RecentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Activity activity;
    private String description;
    private LocalDateTime date;
    private Integer userId;

    public RecentActivity(Activity activity, String description, LocalDateTime date, Integer userId) {
        this.activity = activity;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public RecentActivity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
