package com.kh.hyper.exeption.controller;

public class UserIdFoundException extends RuntimeException{

	public UserIdFoundException(String message) {
		super(message);
	}
}
