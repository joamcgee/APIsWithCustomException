package com.cts.demo.FullStackProfile.controller;

import com.cts.demo.FullStackProfile.exception.ProfileNotFoundException;
import com.cts.demo.FullStackProfile.model.UserProfile;
import com.cts.demo.FullStackProfile.service.UserProfileService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    public static final String SAVE_USER_PROFILE = "/saveProfile";
    public static final String FIND_ALL_USER_PROFILES = "/users";
    public static final String FIND_COUNT_BY_DEPT_NAME = "/userByDepartment";

    @Autowired
    private UserProfileService userService;

    private final Gson gson = new Gson();

    @PostMapping(SAVE_USER_PROFILE)
    public ResponseEntity<String> saveProfile(@Valid @RequestBody UserProfile userProfile) {
        try {
            log.info("--- Saving User Profile Details ---");

            UserProfile user = userService.saveUserProfile(userProfile);
            log.info("UserProfile details store for : {}", user.getId());
            return new ResponseEntity<>(gson.toJson(user), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Failed to persist user profile: {}", userProfile.getEmployeeId());
            return new ResponseEntity<>("Something wrong while persisting user details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(FIND_ALL_USER_PROFILES)
    public ResponseEntity<List<UserProfile>> findAllUserProfiles() {
        log.info("--- Finding All Profile Details ---");
        try {
            List<UserProfile> userProfiles = userService.findAllUserProfiles();
            log.info("Total no of User Profiles : {}", userProfiles.size());
            return new ResponseEntity<>(userProfiles, HttpStatus.OK);
        } catch (Exception e) {
            throw new ProfileNotFoundException(" -- No profile details found -- ");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findUserProfileById(@PathVariable Long id) {
        log.info("--- Finding User Profile Details by ID ---");
        try {
            UserProfile user = userService.findUserById(id).stream().findFirst().orElse(null);
            log.info("UserProfile details for User Id : {}", user.getId());
            return new ResponseEntity<>(gson.toJson(user), HttpStatus.OK);
        } catch (Exception e) {
            throw new ProfileNotFoundException("No details found for ID: -> " + id);
        }
    }

    @GetMapping(FIND_COUNT_BY_DEPT_NAME)
    public ResponseEntity<Map<String, Long>> findUserCountByDepartment() {
        log.info("--- Finding user profile by dept and no of count ---");
        try {
            Map<String, Long> userDeptCount = userService.findUserCountByDepartment();
            userDeptCount.forEach((k, v) -> {
                log.info("DEPT Name {}:, UserProfile Count: {}", k, v);
            });
            return new ResponseEntity<>(userDeptCount, HttpStatus.OK);
        } catch (Exception e) {
            throw new ProfileNotFoundException(" No Profile details by DEPT Name");
        }
    }

}
