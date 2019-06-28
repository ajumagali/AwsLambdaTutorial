package com.zuhlke.lambda.storage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.zuhlke.lambda.models.PersonItem;

public class AwsDynamoDbImpl {
    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    private DynamoDB dynamoDB;

    public AwsDynamoDbImpl() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_2)
                .build();
        dynamoDB = new DynamoDB(client);
        mapper = new DynamoDBMapper(client);
    }

    public void createTable(String tableName, Class clazz) {
        CreateTableRequest request = mapper.generateCreateTableRequest(clazz);
        request.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

        if (checkIfTableExist(tableName))
            System.out.printf("Table '%s' already exists.\n", tableName);
        else {
            client.createTable(request);

            if (checkIfTableExist(tableName))
                System.out.printf("Table '%s' has been successfully created.\n", tableName);
            else
                System.err.printf("Failed to create '%s' table.\n", tableName);
        }

    }

    public void save(PersonItem item) {
        mapper.save(item);
    }

    public boolean checkIfTableExist(String tableName) {
        try {
            Table table = dynamoDB.getTable(tableName);

            return table == null ? false : true;

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
