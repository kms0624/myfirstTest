package com.kh.hyper.exeption;

public class UserIdNotFoundException extends RuntimeException{
	public UserIdNotFoundException(String message) {
		super(message);
	}
}
