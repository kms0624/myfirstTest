package com.kh.hyper.exeption;

public class UserIdFoundException extends RuntimeException{
	public UserIdFoundException(String message) {
		super(message);
	}
}
