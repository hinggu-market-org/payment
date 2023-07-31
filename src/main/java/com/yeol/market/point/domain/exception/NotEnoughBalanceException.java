package com.yeol.market.point.domain.exception;
import com.yeol.market.point.exception.CoffeeShopException;

public class NotEnoughBalanceException extends CoffeeShopException {
    public NotEnoughBalanceException(String message) {
        super(message,null);
    }
}
