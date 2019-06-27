package com.zuhlke.lambda.models;

public class ResponseClass {
    private String greetings;

    public ResponseClass(String greetings) {
        this.greetings = greetings;
    }

    public ResponseClass() {

    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }
}
