package ru.job4j.oop;

public class College {
    public static void main(String[] args) {
        Freshman fr = new Freshman();
        Student st = fr;
        Object o = fr;
        Object object = new Freshman();
        Freshman freshman = (Freshman) object;
    }
}
