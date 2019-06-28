package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.zuhlke.lambda.storage.AwsS3Impl;

import java.util.Map;
import java.util.Random;

public class Hello {
    private AwsS3Impl s3 = new AwsS3Impl();
    private final String BUCKET_NAME = "aws-lambda-test";
    private Random random = new Random();


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
