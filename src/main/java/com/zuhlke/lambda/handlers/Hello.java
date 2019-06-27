package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.Map;

public class Hello {
    public String myHandler(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger(); // Write log entries to CloudWatch logs
        StringBuffer output = new StringBuffer();

        for (String key : input.keySet()) {
            logger.log("received :" + input.get(key));
            output.append(input.get(key));
        }

        return output.toString();
    }
}
