package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class OrdersTests {

    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Get list all orders")
    public void getListOrders () {

        ValidatableResponse response = orderClient.getAllOrders();

        int statusCode = response.extract().statusCode();
        List<Object> orders = response.extract().jsonPath().getList("orders");
        int sizeListOrders = orders.size();

        List<Object> listOfIdOrders = response.extract().jsonPath().getJsonObject("orders.id");
        int sizeListOfIdOrders = listOfIdOrders.size();

        Assert.assertEquals(statusCode, 200);
        Assert.assertFalse(orders.isEmpty());
        Assert.assertEquals(sizeListOfIdOrders, sizeListOrders);

    }


}

