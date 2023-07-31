package com.yeol.market.point.application.exception;

import com.yeol.market.point.exception.CoffeeShopException;
import org.springframework.http.HttpStatus;

public class PointException extends CoffeeShopException {

    public PointException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
