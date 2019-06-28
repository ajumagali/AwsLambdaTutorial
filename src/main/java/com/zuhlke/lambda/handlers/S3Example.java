package com.zuhlke.lambda.handlers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.zuhlke.lambda.blob.AwsS3Impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        /*
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
            String objectKey = "output.txt";
            ObjectMetadata metadata = null;
            InputStream inputStream = null;

            byte[] bytes = output.toString().getBytes("UTF-8");
            inputStream = new ByteArrayInputStream(bytes);

            metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType("text/html");

            s3Client.putObject(BUCKET_NAME, objectKey, inputStream, metadata);

            System.out.println("File uploaded.");
            return  "Success";

        } catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return "error";
        } catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return "error";
    }
}
