package com.nickrossdev.authentication.domain.app;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/protected")
public class AppController {
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/hi")
    public ResponseEntity<?> recursoProtegidoUser() {
        return ResponseEntity.ok("Contenido privado para user");
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin/hi")
    public ResponseEntity<?> recursoProtegidoAdmin() {
        return ResponseEntity.ok("Contenido privado exclusivo de admin");
    }
    @GetMapping("/check-roles")
    public ResponseEntity<?> checkRoles(Authentication authentication) {
        return ResponseEntity.ok(authentication.getAuthorities());
    }


}
