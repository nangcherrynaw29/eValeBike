package integration4.evalebike.service;

import integration4.evalebike.domain.RecentActivity;
import integration4.evalebike.repository.RecentActivityRepository;
import integration4.evalebike.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RecentActivityService {
    private final RecentActivityRepository recentActivityRepository;

    public RecentActivityService(RecentActivityRepository recentActivityRepository) {
        this.recentActivityRepository = recentActivityRepository;
    }

    public RecentActivity save(RecentActivity recentActivity) {
        return recentActivityRepository.save(recentActivity);
    }

    public List<RecentActivity> getAllForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getUserId();

        return recentActivityRepository.findByUserId(userId, Sort.by(Sort.Order.desc("date")));
    }
}
