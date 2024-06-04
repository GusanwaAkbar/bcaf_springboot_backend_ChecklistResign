package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.payload.LoginRequest;
import com.hrs.checklist_resign.payload.SignupRequest;
import com.hrs.checklist_resign.payload.JwtResponse;
import com.hrs.checklist_resign.Model.UserDetailsImpl;
import com.hrs.checklist_resign.repository.UserRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.UserDetailsService;
import com.hrs.checklist_resign.service.UserService;
import com.hrs.checklist_resign.util.JwtUtils;
import com.hrs.checklist_resign.util.UserDetailsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication.getName());

            UserDetailsImpl userDetailObj = (UserDetailsImpl) authentication.getPrincipal();

            // Fetch UserDetail using the username
            UserDetail userDetail = (userDetailsService.findByUsername(userDetailObj.getUsername()));

            // Debugging print statements
            System.out.println("Found user: " + userDetailObj.getUsername());
            System.out.println("Stored password: " + userDetailObj.getPassword());
            System.out.println("UserDetails: " + userDetail);

            // Set the fetched UserDetail in the UserDetailsImpl object
            userDetailObj.setUserDetail(userDetail);

            // Return ApiResponse with successful response
            return ResponseEntity.ok(new ApiResponse<>(new JwtResponse(jwt, userDetailObj.getUsername(), userDetailObj.getAuthorities(), userDetailObj.getUserDetail()), true, "User authenticated successfully", 200));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new ApiResponse<>(null, false, "Invalid username or password", 401));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Error: An internal server error occurred!", 500));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: Username is already taken!", 400));
        }

        // Fetch user details based on the provided username
        List<UserDetail> userDetailsList = fetchUserDetailsByUsername(signUpRequest.getUsername());

        if (userDetailsList.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User details not found for the provided username!", 400));
        }

        // Create a new User object
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setRole("USER");

        // Save the user to generate the ID
        userService.saveUser(user);

        // Fetch the saved user to get the generated ID
        User savedUser = userService.findByUsername(signUpRequest.getUsername()).get();

        // Assuming you want to save the user details as well
        UserDetail userDetail = userDetailsList.get(0);
        userDetail.setUser(savedUser);  // Set the user in userDetails

        // Save the user details
        userDetailsService.saveUserDetails(userDetail);

        return ResponseEntity.ok(new ApiResponse<>("User registered successfully!", true, "User registered successfully", 200));
    }

    private List<UserDetail> fetchUserDetailsByUsername(String username) {
        String sql = "SELECT DISTINCT h.id, h.c_name AS Nama, h.c_email AS Email, " +
                "COALESCE(b.c_location, '-') AS Cabang, COALESCE(d.c_id, '-') AS id_divisi, " +
                "COALESCE(d.c_name, '-') AS Divisi, COALESCE(t.c_name, '-') AS Jabatan, " +
                "COALESCE(h.c_externalUser, 'No') AS External_User " +
                "FROM app_fd_bcafs_hris h WITH (NOLOCK) " +
                "LEFT JOIN dir_user du WITH (NOLOCK) ON (h.id = du.username) " +
                "LEFT JOIN app_fd_bcafs_branch b WITH (NOLOCK) ON (h.c_branchId = b.c_branchCode) " +
                "LEFT JOIN app_fd_bcafs_title t WITH (NOLOCK) ON (h.c_titleId = t.id) " +
                "LEFT JOIN app_fd_bcafs_division d WITH (NOLOCK) ON (t.c_divisionId = d.id) " +
                "WHERE (h.c_active = 'Yes' OR h.c_externaluser = 'Yes') " +
                "AND h.id = ?";

        return jdbcTemplate.query(sql, new UserDetailsRowMapper(), username);
    }

}
