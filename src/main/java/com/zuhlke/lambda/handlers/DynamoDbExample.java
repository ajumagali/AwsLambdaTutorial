package com.zuhlke.lambda.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.zuhlke.lambda.models.PersonItem;
import com.zuhlke.lambda.storage.AwsDynamoDbImpl;

import java.util.Map;
import java.util.Random;

public class DynamoDbExample {
    private AwsDynamoDbImpl dynamoDb = new AwsDynamoDbImpl();
    private Random random = new Random();

    public String myHandler(Map<String, String> input, Context context) {
        LambdaLogger logger = context.getLogger();

        PersonItem personItem = new PersonItem();

        personItem.setId(random.nextInt(1000));
        personItem.setFirstName(input.get("firstName"));
        personItem.setLastName(input.get("lastName"));


        try {
            dynamoDb.createTable("Persons", PersonItem.class);
            dynamoDb.save(personItem);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
