package com.retailwebsite.controller;

import com.retailwebsite.controller.requests.DiscountRequest;
import com.retailwebsite.services.DiscountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/discounts")
public class DiscountController {

    private DiscountService discountService;

    public BigDecimal createDiscount(@Validated @RequestBody DiscountRequest request){
        return discountService.discountCalculation(request.getUser(), request.getBill());
    }
}
