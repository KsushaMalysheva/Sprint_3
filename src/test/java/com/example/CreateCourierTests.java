package com.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateCourierTests {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier can be created with valid data and he can login")
    public void courierCanBeCreatedWithValidDataAndLogin() {

        ValidatableResponse response = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        int statusCode = response.extract().statusCode();
        boolean isCreated = response.extract().path("ok");

        Assert.assertEquals(statusCode, 201);
        Assert.assertTrue(isCreated);
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
    //тест проверяет, что нельзя создать двух одинаковых курьеров
    //Тест падает, так как в документации ошибка одна, а в API другая
    @Test
    @DisplayName("409 Conflict. Courier can not be created with existing credentials")
    public void courierCanNotBeCreatedIfHeWasCreated()  {

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");

        Assert.assertEquals(statusCode, 409);
        Assert.assertEquals(messageError, "Этот логин уже используется");
    }

    //тест проверяет, что нельзя создать курьера с существующим логином
    //Тест падает, так как в документации ошибка одна, а в API другая
    @Test
    @DisplayName("409 Conflict. Courier can not be created with existing login")
    public void courierCanNotBeCreatedWithExistingLogin()  {

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        courier.setPassword(RandomStringUtils.randomAlphabetic(5));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(5));

        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        String messageError = response.extract().path("message");

        Assert.assertEquals(statusCode, 409);
        Assert.assertEquals(messageError, "Этот логин уже используется");
    }



}