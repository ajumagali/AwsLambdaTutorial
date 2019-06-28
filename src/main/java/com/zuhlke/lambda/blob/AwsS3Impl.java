package com.zuhlke.lambda.blob;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public void uploadTextFile(String input, String funcName) {

        try {
            ObjectMetadata meta = null;
            InputStream inputStream = null;
            objectKeyName = String.format("output-%s-%d.txt", funcName, random.nextInt(100));

            byte[] bytes = input.getBytes("UTF-8");
            inputStream = new ByteArrayInputStream(bytes);

            meta = new ObjectMetadata();
            meta.setContentLength(bytes.length);
            meta.setContentType("text/html");

            s3Client.putObject(BUCKET_NAME, objectKeyName, inputStream, meta);
        } catch(AmazonServiceException e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }  catch (IOException e) {
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
