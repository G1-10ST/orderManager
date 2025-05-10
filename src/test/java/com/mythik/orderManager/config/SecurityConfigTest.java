package com.mythik.orderManager.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.mythik.orderManager.constants.CommonConstants.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Qualifier("filterChain")
    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void testPasswordEncoderBean() {
        assertNotNull(passwordEncoder, "PasswordEncoder bean should not be null");
    }

    @Test
    void testUserDetailsService() {
        assertEquals(USER,"USER");
        assertNotNull(userDetailsManager, "InMemoryUserDetailsManager should not be null");
        assertNotNull(userDetailsManager.loadUserByUsername("admin"), "User 'admin' should be loaded");
    }

    @Test
    void testSecurityFilterChain() {
        assertNotNull(securityFilterChain, "SecurityFilterChain should not be null");
    }
}
