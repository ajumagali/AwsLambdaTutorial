package com.zuhlke.lambda.handlers;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.zuhlke.lambda.blob.UploadObject;

import java.util.Map;
import java.util.Random;

public class Hello {
    private UploadObject s3 = new UploadObject();
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
