package com.saurav.ims;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.saurav.ims.security.JwtTokenProvider;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    // Test login endpoint with mocked authentication
    @Test
    void testAuthLoginSuccess() throws Exception {
        String loginJson = """
            {
                "userNameOrEmail": "testuser",
                "password": "password123"
            }
            """;

        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtTokenProvider.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    // Test items endpoint requires authentication
    @Test
    void testItemsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/items"))
               .andExpect(status().isForbidden()); // unauthenticated user trying to access secured endpoint
    }

    // Test items accessible to STAFF role
    @Test
    @WithMockUser(roles = { "STAFF" })
    void testItemsAccessibleForStaff() throws Exception {
        mockMvc.perform(get("/items"))
               .andExpect(status().isOk());
    }

    // Test admin endpoint accessible to ADMIN
    @Test
    @WithMockUser(roles = { "ADMIN" })
    void testAdminEndpointsAccessibleOnlyForAdmin() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
               .andExpect(status().isOk());
    }

    // Test admin endpoint forbidden for STAFF
    @Test
    @WithMockUser(roles = { "STAFF" })
    void testAdminEndpointsForbiddenForStaff() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
               .andExpect(status().isForbidden());
    }

    // Test suppliers endpoint requires authentication
    @Test
    void testSuppliersRequireAuthentication() throws Exception {
        mockMvc.perform(get("/suppliers"))
               .andExpect(status().isForbidden());
    }
}
