package com.example.koperfooddemo;

public class Ingredient {

    String name, print;
    float price;

    public Ingredient(String name, float price){
        this.name = name;
        this.price = price;
        print = name + "       " + price + "€";
    }

    @Override
    public String toString(){
        return print;
    }

    public String getName(){
        return name;
    }

    public float getPrice(){
        return price;
    }
}
