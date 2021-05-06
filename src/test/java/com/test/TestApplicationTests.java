package com.test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;


class TestApplicationTests {

    @Test
    void contextLoads() {
        LocalDate now = LocalDate.now();

        int value = now.getDayOfWeek().getValue();
        System.out.println(value);
    }

    @Test
    void test() {
        LocalTime min = LocalTime.of(0, 0);
        LocalTime now = LocalTime.now();
        LocalTime max = LocalTime.of(23,59);

        System.out.println(now.isAfter(min));

    }



}
