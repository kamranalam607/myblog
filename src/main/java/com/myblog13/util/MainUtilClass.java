package com.myblog13.util;

import org.springframework.data.domain.Sort;

public class MainUtilClass {

    public static void main(String[] args) {

        int number = 11;
        String result;

        //Using the ternary operator to assign a value to 'result'
        result = (number % 2 ==0) ? "Even" : "Odd";

        System.out.println(Sort.Direction.DESC.name());

    }
}
