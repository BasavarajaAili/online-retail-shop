package com.retailwebsite;

import com.retailwebsite.helper.DiscountHelper;
import com.retailwebsite.models.*;
import com.retailwebsite.services.DiscountService;
import com.retailwebsite.services.DiscountServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DiscountTest {

    private User user;

    @Test
    public void testCalculateTotal_GroceriesOnly(){
        System.out.println("Inside calculate grosery only");
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals (300.0,  total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotal_NonGroceriesOnly(){
        System.out.println("Inside calculate non_grosery only");
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.BOOKS, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));

        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateTotal(items);
        assertEquals(300.0, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalMix(){
        System.out.println("Inside calculate Total Mix");
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.BOOKS, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.CLOTHES);
        assertEquals(200.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotal_error() {
        System.out.println("Inside calculate Total error");
        DiscountHelper helper = new DiscountHelper();
        helper.getUserDiscount(null);
    }

    @Test
    public void testCalculateDiscount_10pct() {
        System.out.println("Inside calculate Discount 10%");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.1));
        assertEquals(900.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_50pct() {
        System.out.println("Inside calculate Discount 50%");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.5));
        assertEquals(500.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_0pct() {
        System.out.println("Inside calculate Discount 0%");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(0.0));
        assertEquals(1000.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_100pct() {
        System.out.println("Inside calculate Discount 100%");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(1.0));
        assertEquals(0.0, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_error() {
        System.out.println("Inside calculate Discount error");
        DiscountHelper helper = new DiscountHelper();
        helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(2.0));
    }

    @Test
    public void testGetUserSpecificDiscount_affiliate() {
        System.out.println("Inside discount affiliate");
        User user = new User(UserType.AFFILIATE, LocalDate.now());
        DiscountHelper helper = new DiscountHelper();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.1, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_employee(){
        System.out.println("Inside discount employee");
        User user = new User(UserType.EMPLOYEE, LocalDate.now());
        DiscountHelper helper = new DiscountHelper();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.3, discount.doubleValue(), 0);
    }

    @Test
    public void testGetSpecificDiscount_customer_old(){
        System.out.println("Inside discount customer_old");
        LocalDate joinDate = LocalDate.of(2019, 9, 2);
        User user = new User(UserType.CUSTOMER, joinDate);
        DiscountHelper helper = new DiscountHelper();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.05, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_new(){
        System.out.println("Inside discount customer_new");
        User user = new User(UserType.CUSTOMER, LocalDate.now());
        DiscountHelper helper = new DiscountHelper();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.0, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_null_user() {
        System.out.println("Inside discount customer_null_user");
        DiscountHelper helper = new DiscountHelper();
        helper.getUserDiscount(null);
    }

    @Test
    public void testIsCustomerSince(){
        System.out.println("Inside discount customer_since_2year false");
        DiscountHelper helper = new DiscountHelper();
        LocalDate joinDate = LocalDate.now();
        boolean IsCustomerJoinedTwoYears = helper.isCustomerSince(joinDate, 2);
        assertFalse(IsCustomerJoinedTwoYears);
    }

    @Test
    public void testIsCustomerSince_1year(){
        System.out.println("Inside discount customer_since_1year false");
        DiscountHelper helper = new DiscountHelper();
        LocalDate joinDate = LocalDate.now().minusYears(1);
        boolean IsCustomerJoinedTwoYears = helper.isCustomerSince(joinDate, 2);
        assertFalse(IsCustomerJoinedTwoYears);
    }

    @Test
    public void testIsCustomerSince_2year(){
        System.out.println("Inside discount customer_since_2year True");
        DiscountHelper helper = new DiscountHelper();
        LocalDate joinDate = LocalDate.now().minusYears(2);
        boolean IsCustomerJoinedTwoYears = helper.isCustomerSince(joinDate, 2);
        assertTrue(IsCustomerJoinedTwoYears);
    }

    @Test
    public void testIsCustomerSince_3ears(){
        System.out.println("Inside discount customer_since_3year True");
        DiscountHelper helper = new DiscountHelper();
        LocalDate joinDate = LocalDate.now().minusYears(3);
        boolean IsCustomerJoinedTwoYears = helper.isCustomerSince(joinDate, 2);
        assertTrue(IsCustomerJoinedTwoYears);
    }

    @Test
    public void testCalculateBillsDiscount(){
        System.out.println("Inside calculate bill discount");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal amount = helper.calculateBillDiscount(new BigDecimal(1000), new BigDecimal(100), new BigDecimal(5));
        assertEquals(50, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_2() {
        System.out.println("Inside calculate bill discount_2");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal amount = helper.calculateBillDiscount(new BigDecimal(1000),  new BigDecimal(50),  new BigDecimal(5));
        assertEquals(100, amount.doubleValue(), 0);
    }
    @Test
    public void testCalculateBillsDiscount_3() {
        System.out.println("Inside calculate bill discount_3");
        DiscountHelper helper = new DiscountHelper();
        BigDecimal amount = helper.calculateBillDiscount( new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
        assertEquals(280, amount.doubleValue(), 0);
    }

    @Test
    public void testDiscountServiceCalculate(){
        System.out.println("Inside discount_service_Calculate");
        List<Item> items = new ArrayList<>();
        items.add(new Item(ItemType.GROCERY, new BigDecimal(50.0)));
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(200.0)));
        items.add(new Item(ItemType.BOOKS, new BigDecimal(10.0)));

        Bill bill = new Bill();
        bill.setItems(items);

        DiscountService discountService = new DiscountServiceImpl();
        discountService.discountCalculation(new User(UserType.CUSTOMER, LocalDate.now()), bill);
        DiscountHelper helper = new DiscountHelper();
        BigDecimal amount = helper.calculateBillDiscount(new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
        assertEquals(280, amount.doubleValue(), 0);
    }

}


