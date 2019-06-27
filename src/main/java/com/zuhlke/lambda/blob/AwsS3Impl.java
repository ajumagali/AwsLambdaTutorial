package com.zuhlke.lambda.blob;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class AwsS3Impl {
    private AmazonS3 s3Client;
    private final String REGION = "eu-west-2";
    private final String BUCKET_NAME = "aws-lambda-test-mancs";
    private String objectKeyName;
    private Random random = new Random();

    public AwsS3Impl() {
        initConnection();
        createBucket();
    }

    private void initConnection() {
        try {
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.EU_WEST_2)
                    .build();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    private void createBucket() {
        try {
            if (!s3Client.doesBucketExistV2(BUCKET_NAME)) {
                s3Client.createBucket(new CreateBucketRequest(BUCKET_NAME));
                String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(BUCKET_NAME));
                System.out.println("Bucket location: " + bucketLocation);
            }
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }

    public void uploadObject(String str, String funcName) {
        try{
            objectKeyName = "output-" + funcName + "-" + random.nextInt(100);
            s3Client.putObject(BUCKET_NAME, objectKeyName, str);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }

    private String objectToJsonString(Object obj) {
        String jsonString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(obj);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
