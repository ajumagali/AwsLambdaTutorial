package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.zuhlke.lambda.models.Person;
import com.zuhlke.lambda.models.Greeting;

public class HelloPojo implements RequestHandler<Person, Greeting> {

    public Greeting handleRequest(Person person, Context context) {
        String greetingString = String.format("Hello %s, %s.",
                                                person.getFirstName(),
                                                person.getLastName());
        LambdaLogger logger = context.getLogger();
        logger.log(greetingString);
        return new Greeting(greetingString);
    }
}