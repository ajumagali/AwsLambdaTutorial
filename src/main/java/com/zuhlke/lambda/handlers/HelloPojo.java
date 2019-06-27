package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.zuhlke.lambda.models.RequestClass;
import com.zuhlke.lambda.models.ResponseClass;

public class HelloPojo implements RequestHandler<RequestClass, ResponseClass> {

    public ResponseClass handleRequest(RequestClass requestClass, Context context) {
        String greetingString = String.format("Hello %s, %s.",
                                                requestClass.getFirstName(),
                                                requestClass.getLastName());
        LambdaLogger logger = context.getLogger();
        logger.log(greetingString);
        return new ResponseClass(greetingString);
    }
}