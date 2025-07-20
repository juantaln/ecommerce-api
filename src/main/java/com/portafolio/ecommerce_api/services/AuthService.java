package com.portafolio.ecommerce_api.services;

import com.portafolio.ecommerce_api.dto.RegisterDto;
import com.portafolio.ecommerce_api.entities.Role;
import com.portafolio.ecommerce_api.entities.User;
import com.portafolio.ecommerce_api.repositories.RoleRepository;
import com.portafolio.ecommerce_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import com.portafolio.ecommerce_api.dto.LoginDto;
import com.portafolio.ecommerce_api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User register(RegisterDto registerDto) {
     

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

   
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

        public String login(LoginDto loginDto) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
            )
        );

      
        SecurityContextHolder.getContext().setAuthentication(authentication);

  
        return jwtTokenProvider.generateToken(authentication);
    }
}