package com.saurav.ims.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.ims.model.AuthResponse;
import com.saurav.ims.model.LoginRequest;
import com.saurav.ims.model.RegisterRequest;
import com.saurav.ims.model.Role;
import com.saurav.ims.model.RoleName;
import com.saurav.ims.model.User;
import com.saurav.ims.repository.RoleRepository;
import com.saurav.ims.repository.UserRepository;
import com.saurav.ims.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ims/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		
		if(userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("username_taken");
		}
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("email_taken");
        }
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		Role defaultRole = roleRepository.findByName(RoleName.ROLE_STAFF)
				.orElseThrow(() -> 	new RuntimeException("Default role not found"));
		
		user.setRoles(Set.of(defaultRole));
		
		userRepository.save(user);
		
		return ResponseEntity.ok("user_registerd");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserNameOrEmail(),request.getPassword())
					);
			
			UserDetails principal = (UserDetails)auth.getPrincipal();
			String token = jwtTokenProvider.generateToken(principal);
			
			return ResponseEntity.ok(new AuthResponse(token));
			
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("invalid_credentials");
		} catch (DisabledException e) {
			return ResponseEntity.status(403).body("account_disabled");
		}
		
	}

}
