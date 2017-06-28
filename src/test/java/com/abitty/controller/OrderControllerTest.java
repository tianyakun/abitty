package com.abitty.controller;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/28.
 */
public class OrderControllerTest {

    @Test
    public void test() {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(new Date());

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(new Date());

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        System.out.println(day2 - day1);;

    }

}