package com.retailwebsite.controller.requests;

import com.retailwebsite.models.Bill;
import com.retailwebsite.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountRequest {
    private User user;
    private Bill bill;
}
