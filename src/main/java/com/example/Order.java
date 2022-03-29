package com.example;

import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class Order {

    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;

    public Order(String firstName,String lastName, String address, String metroStation,
                 String phone,int rentTime,String deliveryDate, String comment, String[] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandomOrder(){

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String metroStation = faker.address().state();
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = faker.number().randomDigit();
        String deliveryDate =  "02.04.2022";//new SimpleDateFormat("DD.MM.yyyy").format(getInstance().getTime());
        String comment = faker.name().title();
        String[] color = null;

        return  new Order(firstName,lastName,address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
    }

    public static Order getOrderWithoutColor(){

        Order order = getRandomOrder();
        order.color = null;

        return order;
    }
    public static Order getOrderWithColor(String[] color){

        Order order = getRandomOrder();
        order.color = color;

        return order;
    }



}

