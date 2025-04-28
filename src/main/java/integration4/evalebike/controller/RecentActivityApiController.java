package integration4.evalebike.controller;

import integration4.evalebike.domain.RecentActivity;
import integration4.evalebike.service.RecentActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecentActivityApiController {
    private final RecentActivityService recentActivityService;

    public RecentActivityApiController(RecentActivityService recentActivityService) {
        this.recentActivityService = recentActivityService;
    }

    @GetMapping("/api/recent-activity")
    public ResponseEntity<List<RecentActivity>> getRecentActivities() {
        List<RecentActivity> recentActivities = recentActivityService.getAllForCurrentUser();
        return ResponseEntity.ok(recentActivities);
    }
}
