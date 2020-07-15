package com.moome;

import java.util.ArrayList;

public class Statistics {
    public static ArrayList<String> strings = new ArrayList<>();
    public static ArrayList<Integer> numbers = new ArrayList<>();
    
    public static void add(String name, int number) {
        strings.add(name);
        numbers.add(number);
    }
    
    public static void set(String name, int number) {
        numbers.set(strings.indexOf(name), Integer.valueOf(number));
    }
    
    public static int get(String name) {
        return numbers.get(strings.indexOf(name)).intValue();
    }
}
