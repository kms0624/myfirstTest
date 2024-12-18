package com.kh.hyper.exeption.controller;

public class UserFoundException extends RuntimeException{

	public UserFoundException(String message) {
		super(message);
	}
}
