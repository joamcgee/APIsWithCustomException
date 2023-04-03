package com.cts.demo.FullStackProfile.service;

import com.cts.demo.FullStackProfile.model.UserProfile;
import com.cts.demo.FullStackProfile.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userRepository;

    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userRepository.save(userProfile);
    }

    public Optional<UserProfile> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<UserProfile> findAllUserProfiles() {
        return userRepository.findAll();
    }

    public Map<String, Long> findUserCountByDepartment() {
        Map<String, Long> userCountByDept = new HashMap<>();
        List<UserProfile> allUserProfiles = userRepository.findAll();
        if (allUserProfiles.size() > 0) {
            userCountByDept = allUserProfiles
                    .stream()
                    .collect(Collectors.groupingBy(UserProfile::getDepartment, Collectors.counting()));
        }
        return userCountByDept;
    }
}
