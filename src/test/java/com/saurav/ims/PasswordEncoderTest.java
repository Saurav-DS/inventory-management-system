package com.saurav.ims;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Test
//	void generateBCryptHashes() {
//		System.out.println(passwordEncoder.getClass());
//		System.out.println("Admin password: " + passwordEncoder.encode("admin123"));
//		System.out.println("Manager password: " + passwordEncoder.encode("manager123"));
//		System.out.println("Staff password: " + passwordEncoder.encode("staff123"));
//	}

	@Test
	void testAdminPasswordMatches() {
		String rawPassword = "admin123";
		String encodedPassword = "$2a$10$spw2CFEf8WZUwHeNZEl7IeJVwWjuhHNHe6zkitMtkrjpVPWErZjMy";
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}

	@Test
	void testManagerPasswordMatches() {
		String rawPassword = "manager123";
		String encodedPassword = "$2a$10$eGxNyXlngKqlnioeBHEMMO2QQp/PjaspqdA5G48JnXjqn.oZg64ri";
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}

	@Test
	void testStaffPasswordMatches() {
		String rawPassword = "staff123";
		String encodedPassword = "$2a$10$bPd4fS.9xD3VTekY7HGoDeDMSWGhTarbpuFPl467NZ.rAmCE74PMy";
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}
}
