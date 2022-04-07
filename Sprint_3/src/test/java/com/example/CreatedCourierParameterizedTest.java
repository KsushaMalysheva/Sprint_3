package com.example;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreatedCourierParameterizedTest {

    private final Courier courier;
    private final int expectedCodeResult;
    private final String expectedMessage;

    public CreatedCourierParameterizedTest(Courier courier, int expectedCodeResult, String expectedMessage){
        this.courier = courier;
        this.expectedCodeResult = expectedCodeResult;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[] getTestData() {
        return new Object[][] {
                {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithFirstnameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithLoginAndPassword(), 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    //Тест проверяет создание курьера с различными невалидными данными
    // на данный момент с 4 набором параметров падает тест, так как в документации не указано, что параметр firstName необязательный
    //  а в API он необязательный
    @Test
    @DisplayName("Courier can not be created with invalid data")
    public void createCourierWithInvalidData() {
        ValidatableResponse response = new CourierClient().create(courier);

        int actualCodeResult = response.extract().statusCode();
        String actualMessage = response.extract().path("message");

        Assert.assertEquals(actualCodeResult, expectedCodeResult);
        Assert.assertEquals(actualMessage, expectedMessage);

    }


}