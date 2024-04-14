package ru.paermakov;

public class Employee {
    enum Position {
        ENGINEER, LABORANT, CLEANER
    }

    private String name;
    private int age;
    private Position position;

    public Employee(String name, int age, Position position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }
}
