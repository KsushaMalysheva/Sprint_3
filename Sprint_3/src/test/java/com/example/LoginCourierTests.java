package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginCourierTests {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier can login")
    public void courierCanLoginWithValidCredentials() {

        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        Assert.assertEquals(statusCode, 200);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}