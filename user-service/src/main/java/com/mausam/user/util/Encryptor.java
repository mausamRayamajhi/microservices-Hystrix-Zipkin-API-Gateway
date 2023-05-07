package com.mausam.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryptor {
	public static String hashCode(String string) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(string);
	}
}
