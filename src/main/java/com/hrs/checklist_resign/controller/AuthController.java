package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.PostChangeRoleDTO;
import com.hrs.checklist_resign.payload.LoginRequest;
import com.hrs.checklist_resign.payload.JwtResponse;
import com.hrs.checklist_resign.Model.UserDetailsImpl;
import com.hrs.checklist_resign.payload.SignupRequest;
import com.hrs.checklist_resign.repository.UserRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.UserDetailsService;
import com.hrs.checklist_resign.service.UserService;
import com.hrs.checklist_resign.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserService userService;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;




    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userService.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: Username is already taken!", 400));
        }

        // Fetch user details based on the provided username
        List<UserDetail> userDetailsList = userDetailsService.fetchUserDetailsByUsername(signUpRequest.getUsername());

        if (userDetailsList.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User details not found for the provided username!", 400));
        }

        // Create a new User object
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setRoles("USER");

        // Save the user to generate the ID
        userService.saveUser(user);

        // Fetch the saved user to get the generated ID
        User savedUser = userService.findByUsername(signUpRequest.getUsername()).get();

        // Assuming you want to save the user details as well
        UserDetail userDetail = userDetailsList.get(0);
        userDetail.setUser(savedUser);  // Set the user in userDetails

        // Save the user details
        userDetailsService.saveUserDetails(userDetail);

        return ResponseEntity.ok(new ApiResponse<>(user,true, "User registered successfully", 200));
    }





    @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication.getName());

                UserDetailsImpl userDetailObj = (UserDetailsImpl) authentication.getPrincipal();

                // Fetch or create UserDetail using the username
                UserDetail userDetail = fetchOrCreateUserDetail(userDetailObj.getUsername(), null);

                // Debugging print statements
                System.out.println("Found user: " + userDetailObj.getUsername());
                System.out.println("Stored password: " + userDetailObj.getPassword());
                System.out.println("UserDetails: " + userDetail);

                // Set the fetched UserDetail in the UserDetailsImpl object
                userDetailObj.setUserDetail(userDetail);

                // Fetch or create UserDetail for the atasan (supervisor)
                String nipAtasan = userDetail.getNipAtasan();
                UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);

                // Return ApiResponse with successful response
                return ResponseEntity.ok(new ApiResponse<>(
                        new JwtResponse(jwt, userDetailObj.getUsername(), userDetailObj.getAuthorities(), userDetail, userDetailAtasan),
                        true, "User authenticated successfully", 200));
            } catch (BadCredentialsException e) {
                e.printStackTrace();
                return ResponseEntity.status(401).body(new ApiResponse<>(null, false, "Invalid username or password", 401));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Error: An internal server error occurred!", 500));
            }
        }

        @PostMapping("/signin/V3")
        public ResponseEntity<ApiResponse<JwtResponse>> authenticateUserWithLDAPV3(@RequestBody LoginRequest loginRequest) {
            try {
                // Authenticate with LDAP
                if (!ldapAuthenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
                    return ResponseEntity.status(401).body(new ApiResponse<>(null, false, "Invalid LDAP credentials", 401));
                }

                // Generate JWT token
                String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

                // Retrieve or create user
                User user = userRepository.findByUsername(loginRequest.getUsername())
                        .orElseGet(() -> createNewUser(loginRequest.getUsername()));

                if (user == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User not found in HRIS Database", 400));
                }

                // Retrieve or create user detail
                UserDetail userDetail = fetchOrCreateUserDetail(loginRequest.getUsername(), null);
                if (userDetail == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User detail not found in HRIS Database", 400));
                }

                // Retrieve or create supervisor detail
                UserDetail userDetailAtasan = fetchOrCreateUserDetail(userDetail.getNipAtasan(), null);
                if (userDetailAtasan == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: Supervisor details not found in HRIS Database", 400));
                }

                // Retrieve or create supervisor user
                User userAtasan = userRepository.findByUsername(userDetail.getNipAtasan())
                        .orElseGet(() -> createNewUser(userDetail.getNipAtasan()));

                userDetailAtasan.setUser(userAtasan);
                userDetailsService.saveUserDetails(userDetailAtasan);

                UserDetailsImpl userDetailObj = new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getAuthorities(), user.getUserDetails());
                userDetailObj.setUserDetail(userDetail);

                return ResponseEntity.ok(new ApiResponse<>(
                        new JwtResponse(jwt, userDetailObj.getUsername(), userDetailObj.getAuthorities(), userDetailObj.getUserDetail(), userDetailAtasan),
                        true,
                        "User authenticated successfully with LDAP",
                        200
                ));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Error: An internal server error occurred!", 500));
            }
        }

        @PostMapping("/changeRole/v2")
        public ResponseEntity<ApiResponse<User>> changeUserRoleV2(@RequestBody PostChangeRoleDTO postChangeRoleDTO) {
            try {
                String nipKaryawan = postChangeRoleDTO.getUsername();
                UserDetail userDetail = fetchOrCreateUserDetail(nipKaryawan, null);

                if (userDetail == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User detail not found in HRIS Database", 400));
                }


                if (!userService.changeUserRole(nipKaryawan, postChangeRoleDTO.getNewRole())) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User not found", 400));
                } 

                Optional<User> userOpt = userService.findByUsername(nipKaryawan);
                User user = userOpt.get();

                return ResponseEntity.ok(new ApiResponse<>(user, true, "User role changed successfully", 200));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "An error occurred while changing the user role", 500));
            }
        }

        private boolean ldapAuthenticate(String username, String password) {
            String url = "http://192.168.29.71:12103/EnterpriseAuthentication/AuthenticateUserV2";
            HttpHeaders headers = createBasicAuthHeaders("bcafapps", "Admin123");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("TrxId", null);
            requestBody.put("Credentials", Map.of("UserId", username, "Password", password));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            try {
                ResponseEntity<Map> response = new RestTemplate().postForEntity(url, request, Map.class);
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> responseHeader = (Map<String, Object>) responseBody.get("ResponseHeader");
                return "0".equals(responseHeader.get("ErrorCode"));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private HttpHeaders createBasicAuthHeaders(String username, String password) {
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authHeader);

            return headers;
        }

        private User createNewUser(String username) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(username);
            user.setRoles("USER");

            List<UserDetail> userDetailList = userDetailsService.fetchUserDetailsByUsername(username);
            if (userDetailList.isEmpty()) {
                return null;
            }

            UserDetail userDetail = userDetailList.get(0);
            userService.saveUser(user);
            userDetail.setUser(user);
            userDetailsService.saveUserDetails(userDetail);

            return user;
        }

        private UserDetail fetchOrCreateUserDetail(String nipAtasanFromDTO, String nipAtasanFromUserDetail) {
            String nipAtasan = nipAtasanFromDTO != null && !nipAtasanFromDTO.isEmpty() ? nipAtasanFromDTO : nipAtasanFromUserDetail;

            if (nipAtasan == null || nipAtasan.isEmpty()) {
                return null;
            }

            System.out.println("================nipAtasan======================");
            System.out.println(nipAtasan);

            UserDetail userDetailAtasan = userDetailsService.findByUsername(nipAtasan);

            System.out.println("user detail atasan isinya apa");
            //System.out.println(userDetailAtasan.getNama());

            if (userDetailAtasan == null) {
                List<UserDetail> userDetailsList = userDetailsService.fetchUserDetailsByUsername(nipAtasan);

                System.out.println("user detail atasan");

                if (!userDetailsList.isEmpty()) {
                    userDetailAtasan = userDetailsList.get(0);

                    Optional<User> atasanUserOpt = userService.findByUsername(nipAtasan);

                    User userAtasan;
                    if (atasanUserOpt.isEmpty()) {
                        userAtasan = new User();
                        userAtasan.setUsername(nipAtasan);
                        userAtasan.setPassword(nipAtasan); // Consider using a more secure default password
                        userAtasan.setRoles("USER");
                        userService.saveUser(userAtasan);
                    } else {
                        userAtasan = atasanUserOpt.get();
                    }

                    userDetailAtasan.setUser(userAtasan);
                    userDetailsService.saveUserDetails(userDetailAtasan);
                } else {
                    return null; // If the NIP Atasan is not found in HRIS
                }
            } else {
                // Update userDetail Atasan
                Optional<User> userOpt = userService.findByUsername(nipAtasan);
                User user = userOpt.get();

                System.out.println("masuksini =================");
                // Fetch data from HRIS
                List<UserDetail> userDetailsList = userDetailsService.fetchUserDetailsByUsername(nipAtasan);

                // Update user detail's user
                if (!userDetailsList.isEmpty()) {
                    UserDetail updatedUserDetail = userDetailsList.get(0);

                    // Merge fields from HRIS data to existing user detail
                    userDetailAtasan.setCabang(updatedUserDetail.getCabang());
                    userDetailAtasan.setDivisi(updatedUserDetail.getDivisi());
                    userDetailAtasan.setEmail(updatedUserDetail.getEmail());
                    userDetailAtasan.setIdDivisi(updatedUserDetail.getIdDivisi());
                    userDetailAtasan.setJabatan(updatedUserDetail.getJabatan());
                    userDetailAtasan.setNama(updatedUserDetail.getNama());
                    userDetailAtasan.setNipAtasan(updatedUserDetail.getNipAtasan());
                    userDetailAtasan.setUser(user);

                    userDetailsService.saveUserDetails(userDetailAtasan);
                }

                // Update user's userDetail
                user.setUserDetails(userDetailAtasan);
            }

            return userDetailAtasan;
        }
    }



