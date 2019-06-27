package com.zuhlke.lambda.models;

public class Greeting {
    private String greetings;

    public Greeting(String greetings) {
        this.greetings = greetings;
    }

    public Greeting() {

    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }
}
