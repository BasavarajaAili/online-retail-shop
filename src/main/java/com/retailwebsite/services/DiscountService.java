package com.retailwebsite.services;

import com.retailwebsite.models.Bill;
import com.retailwebsite.models.User;

import java.math.BigDecimal;

public interface DiscountService {

    BigDecimal discountCalculation(User user, Bill bill);
}
