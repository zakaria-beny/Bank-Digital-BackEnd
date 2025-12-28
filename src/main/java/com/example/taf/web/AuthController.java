package com.example.taf.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taf.services.AuthService;
import com.example.taf.security.JwtUtil;
import com.example.taf.entities.User;
import lombok.AllArgsConstructor;

import java.util.Map;

@RestController("profileAuthController")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PutMapping("/auth/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String email = payload.get("email");
            String newPassword = payload.get("newPassword");

            User updated = authService.updateCurrentUser(username, email, newPassword);
            String token = jwtUtil.generateToken(updated.getUsername());

            return ResponseEntity.ok(Map.of(
                    "message", "Profile updated successfully",
                    "token", token,
                    "username", updated.getUsername(),
                    "role", updated.getRole()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Unauthorized";
            if ("Unauthenticated".equalsIgnoreCase(msg)) {
                return ResponseEntity.status(401).body(Map.of("message", "Unauthorized"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", msg));
        }
    }
}
