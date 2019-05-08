package com.bjss.exception;

/**
 * Exception to represent no item exists in stock by the given name
 * 
 * @author Suresh
 *
 */
public class NoSuchItemException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NoSuchItemException(String message) {
		super(message);
	}
}
