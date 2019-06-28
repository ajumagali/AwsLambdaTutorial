package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.zuhlke.lambda.storage.AwsS3Impl;

import java.util.Map;

public class S3Example {
    private AwsS3Impl awsS3Impl = new AwsS3Impl();

    public String myHandler(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        StringBuffer output = new StringBuffer();

        for (String key: input.keySet()) {
            logger.log("key: " + key + " value: " + input.get(key));
            output.append(key + ": " + input.get(key));
        }

        try {
            awsS3Impl.uploadTextFile(output.toString(), context.getFunctionName());
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "error";
    }
}
