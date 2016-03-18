package com.tenx.ms.retail.exception;

public class RetailNoEnoughStockException extends Exception {
	private static final long serialVersionUID = 1L;

    public RetailNoEnoughStockException(String message) {
        super(message);
    }
}
