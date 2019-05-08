package com.bjss.exception;

/**
 * Exception class to represent invalid promotion configuration
 * 
 * @author Suresh
 *
 */
public class InvalidPromotionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidPromotionException(String message) {
		super(message);
	}
}
