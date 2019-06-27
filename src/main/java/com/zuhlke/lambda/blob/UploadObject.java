package com.zuhlke.lambda.blob;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

public class UploadObject {
    private AmazonS3 s3Client;
    private final String REGION = "eu-west-2";
    private final String BUCKET_NAME = "aws-lambda-test";
    private String objectKeyName;
    private Random random = new Random();

    public UploadObject() {
        initConnection();
        createBucket();
    }

    public void initConnection() {
        try {
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(REGION)
                    .withCredentials(new ProfileCredentialsProvider())
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

    public void uploadObject(String str) {
        try{
            objectKeyName = "test-" + random.nextInt(100);
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
