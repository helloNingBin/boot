package com.example.boot.bean;

public class Persion {
    private String name;
    private int age;
    private Car car;

    public Persion() {
    }

    public Persion(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void starDay(){
        System.out.println("star a new day........");
    }
    public void doWork(){
        System.out.println("work hard a day.......");
    }
    public void sleep(){
        System.out.println("go to sleep...........");
    }

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
