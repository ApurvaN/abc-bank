package com.abc.exception;
public class AccountOperationException extends Exception {

	public AccountOperationException() {
	}

	public AccountOperationException(String message) {
		super(message);
	}

	public AccountOperationException(Throwable cause) {
		super(cause);
	}

	public AccountOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
