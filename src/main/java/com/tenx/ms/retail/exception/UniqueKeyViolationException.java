package com.tenx.ms.retail.exception;

public class UniqueKeyViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public UniqueKeyViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
