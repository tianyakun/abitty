package com.abitty.controller;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by kkk on 17/6/18.
 */
public class UserControllerTest {

    @Test
    public void test() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

        System.out.println(DATE_FORMAT.format(new Date()));
    }

}