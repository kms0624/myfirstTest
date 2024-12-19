package com.kh.hyper.exeption.controller;
public class TooLargeValueException extends RuntimeException{
	
	public TooLargeValueException(String message) {
		super(message);
	}
}