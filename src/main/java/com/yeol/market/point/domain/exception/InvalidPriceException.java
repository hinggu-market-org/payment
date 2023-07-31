package com.yeol.market.point.domain.exception;

import com.yeol.market.point.exception.CoffeeShopException;
import org.springframework.http.HttpStatus;

public class InvalidPriceException extends CoffeeShopException {

    public InvalidPriceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
