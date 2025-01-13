package com.vipjoker.ecomerce.exception;

public class ProductDontFoundException extends RuntimeException {
    public ProductDontFoundException(String message) {
        super(message);
    }
}
