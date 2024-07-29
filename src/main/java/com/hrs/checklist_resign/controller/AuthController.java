package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.PostChangeRoleDTO;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

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

        return ResponseEntity.ok(new ApiResponse<>("User registered successfully!", true, "User registered successfully", 200));
    }



    @PostMapping("/changeRole")
    public ResponseEntity<?> changeUserRole(@RequestBody PostChangeRoleDTO postChangeRoleDTO) {
        boolean isRoleChanged = userService.changeUserRole(postChangeRoleDTO.getUsername(), postChangeRoleDTO.getNewRole());

        if (isRoleChanged) {
            return ResponseEntity.ok(new ApiResponse<>(null, true, "User role changed successfully", 200));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User not found", 400));
        }
    }

    @PostMapping("/changeRole/v2")
    public ResponseEntity<?> changeUserRoleV2(@RequestBody PostChangeRoleDTO postChangeRoleDTO) {
        Optional<User> userOptional = userService.findByUsername(postChangeRoleDTO.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRoles(postChangeRoleDTO.getNewRole());
            userService.saveUser(user);

            return ResponseEntity.ok(new ApiResponse<>(null, true, "User role changed successfully", 200));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User not found", 400));
        }
    }



    private boolean ldapAuthenticate(String username, String password) {
        String url = "http://192.168.29.71:12103/EnterpriseAuthentication/AuthenticateUserV2";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //add basic auth
        String auth = "bcafapps:Admin123";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("TrxId", null);
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("UserId", username);
        credentials.put("UserName", null);
        credentials.put("Password", password);
        requestBody.put("Credentials", credentials);

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




    private User createNewUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(username); // Empty password as we're using LDAP
        user.setRoles("USER"); // Set default role




        //CREATE AND FETCH USER DETAIL FROM DATABASE
        List<UserDetail> userDetailList = userDetailsService.fetchUserDetailsByUsername(username);
        UserDetail userDetail = new UserDetail();

        //IF USER DETAIL NOT EMPTY
        if (!userDetailList.isEmpty())
        {
            userDetail = userDetailList.get(0);

            user = userService.saveUser(user);

            //set user
            userDetail.setUser(user);

            //save user and user detail
            user.setUserDetails(userDetail);
        } else {
            return  null;
        }

        userDetailsService.saveUserDetails(userDetail);


        return user;
    }

    @PostMapping("/signin/V3")
    public ResponseEntity<?> authenticateUserWithLDAPV3(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate against LDAP
            if (ldapAuthenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
                System.out.println("===========METHOD CALLED ====================");

                // If LDAP authentication is successful, generate JWT token
                String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

                // Fetch or create user in your app's database
                User user = userRepository.findByUsername(loginRequest.getUsername())
                        .orElseGet(() -> createNewUser(loginRequest.getUsername()));

                if (user == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User not found in HRIS Database", 400));
                }

                // Fetch or create user detail
                UserDetail userDetail = fetchOrCreateUserDetail(loginRequest.getUsername(), null);
                if (userDetail == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: User detail not found in HRIS Database", 400));
                }

                // Update user's userDetail
                user.setUserDetails(userDetail);
                userService.saveUser(user);

                // Fetch or create user detail for atasan
                String nipAtasan = userDetail.getNipAtasan();
                UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);
                if (userDetailAtasan == null) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(null, false, "Error: Supervisor details not found in HRIS Database", 400));
                }

                // Create UserDetailsImpl object
                UserDetailsImpl userDetailObj = new UserDetailsImpl(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities(),
                        user.getUserDetails()
                );
                userDetailObj.setUserDetail(userDetail);

                // Return ApiResponse with successful response
                return ResponseEntity.ok(new ApiResponse<>(
                        new JwtResponse(jwt, userDetailObj.getUsername(), userDetailObj.getAuthorities(), userDetailObj.getUserDetail(), userDetailAtasan),
                        true,
                        "User authenticated successfully with LDAP",
                        200
                ));
            } else {
                return ResponseEntity.status(401).body(new ApiResponse<>(null, false, "Invalid LDAP credentials", 401));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(null, false, "Error: An internal server error occurred!", 500));
        }
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




}
