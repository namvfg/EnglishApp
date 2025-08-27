/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.controllers;

import com.cloudinary.Cloudinary;
import com.ndd.DTO.LoginRequestDTO;
import com.ndd.components.JwtService;
import com.ndd.enums.UserRole;
import com.ndd.pojo.User;
import com.ndd.service.CloudinaryService;
import com.ndd.service.UserService;
import com.nimbusds.jose.JOSEException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            String rawRole = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("");
            String role = rawRole.replace("ROLE_", "");

            // Truy xuất user để lấy thêm thông tin
            User user = userService.getUserByEmail(email);

            String token = jwtService.generateToken(email, UserRole.valueOf(role));

            Map<String, String> result = new HashMap<>();
            result.put("id", user.getId().toString());
            result.put("token", token);
            result.put("role", role);
            result.put("email", user.getEmail());
            result.put("firstName", user.getFirstName());
            result.put("lastName", user.getLastName());
            result.put("avatar", user.getAvatarUrl());

            return ResponseEntity.ok(result);

        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (JOSEException ex) {
            Logger.getLogger(ApiUserController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Token generation failed"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            // Kiểm tra tên
            if (first_name.isBlank() || last_name.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Thông tin không hợp lệ"));
            }

            // Email format
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email không hợp lệ"));
            }

            // Password phải có ít nhất 1 chữ và 1 số 
            if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Mật khẩu phải từ 6 ký tự và chứa cả chữ lẫn số"));
            }

            // Kiểm tra xác minh OTP
            String verifyKey = "otp:verified:" + email;
            String isVerified = redisTemplate.opsForValue().get(verifyKey);
            if (isVerified == null || !"true".equals(isVerified)) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Bạn chưa xác minh OTP hoặc OTP đã hết hạn"));
            }

            // Kiểm tra email đã tồn tại
            if (userService.getUserByEmail(email) != null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Email đã tồn tại"));
            }

            // Upload ảnh lên Cloudinary
            String imageUrl = cloudinaryService.uploadImage(imageFile, "avatar").get("secure_url").toString();

            // Tạo user
            User user = new User();
            user.setFirstName(first_name);
            user.setLastName(last_name);
            user.setEmail(email);
            user.setPassword(password);
            user.setAvatarUrl(imageUrl);
            user.setRole(UserRole.USER); // default role
            user.setCreatedDate(new Date());
            user.setUpdatedDate(new Date());

            boolean success = userService.addUser(user);

            // Xóa key xác minh OTP
            redisTemplate.delete(verifyKey);

            if (success) {
                return ResponseEntity.ok(Map.of("message", "Đăng ký thành công " + first_name + " " + last_name));
            } else {
                return ResponseEntity.status(500).body(Map.of("error", "Lỗi trong quá trình lưu người dùng"));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Lỗi khi upload ảnh: " + ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Lỗi hệ thống: " + ex.getMessage()));
        }
    }
}
