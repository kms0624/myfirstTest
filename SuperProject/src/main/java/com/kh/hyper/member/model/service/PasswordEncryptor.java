package com.kh.hyper.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncryptor {
	
	// 책임 전가 => BCrypt 대신 Argon2 같은거로 변경하면 여기서만 변경하면 된다.
	private final BCryptPasswordEncoder passwordEncoder;
	
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	

}
