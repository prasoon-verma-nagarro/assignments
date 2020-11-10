package org.nagarro.assignment.exception;

public class RetailStoreException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetailStoreException(String message) {
		super(message);
	}

	public RetailStoreException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
