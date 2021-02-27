package com.example.boot.bean;

public class Persion {
    private String name;
    private int age;

    public Persion() {
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
    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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
