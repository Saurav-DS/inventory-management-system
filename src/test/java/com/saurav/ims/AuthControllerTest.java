package com.saurav.ims;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.saurav.ims.controller.AuthController;
import com.saurav.ims.model.AuthResponse;
import com.saurav.ims.model.LoginRequest;
import com.saurav.ims.model.RegisterRequest;
import com.saurav.ims.model.Role;
import com.saurav.ims.model.RoleName;
import com.saurav.ims.model.User;
import com.saurav.ims.repository.RoleRepository;
import com.saurav.ims.repository.UserRepository;
import com.saurav.ims.security.JwtTokenProvider;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegisterUsernameTaken() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("admin");
        req.setEmail("admin@example.com");
        req.setPassword("pass");

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authController.register(req);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("username_taken", response.getBody());
    }

    @Test
    void testRegisterEmailTaken() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("newuser");
        req.setEmail("admin@example.com");
        req.setPassword("pass");

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authController.register(req);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("email_taken", response.getBody());
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("newuser");
        req.setEmail("newuser@example.com");
        req.setPassword("pass");

        Role defaultRole = new Role();
        defaultRole.setName(RoleName.ROLE_STAFF);

        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(RoleName.ROLE_STAFF)).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode("pass")).thenReturn("hashedPass");

        ResponseEntity<?> response = authController.register(req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("user_registerd", response.getBody());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        LoginRequest req = new LoginRequest();
        req.setUserNameOrEmail("admin");
        req.setPassword("pass");

        Authentication authMock = mock(Authentication.class);
        UserDetails userDetailsMock = mock(UserDetails.class);

        when(authMock.getPrincipal()).thenReturn(userDetailsMock);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);
        when(jwtTokenProvider.generateToken(userDetailsMock)).thenReturn("jwtToken");

        ResponseEntity<?> response = authController.login(req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof AuthResponse);
        assertEquals("jwtToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    void testLoginInvalidCredentials() {
        LoginRequest req = new LoginRequest();
        req.setUserNameOrEmail("admin");
        req.setPassword("wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));

        ResponseEntity<?> response = authController.login(req);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("invalid_credentials", response.getBody());
    }
}
