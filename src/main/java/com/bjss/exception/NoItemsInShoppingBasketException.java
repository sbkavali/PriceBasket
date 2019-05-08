package com.bjss.exception;

/**
 * The exception class for empty shopping basket
 * 
 * @author Suresh
 *
 */
public class NoItemsInShoppingBasketException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NoItemsInShoppingBasketException(String message) {
		super(message);
	}
}
