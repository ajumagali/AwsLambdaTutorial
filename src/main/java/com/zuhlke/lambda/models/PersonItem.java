package com.zuhlke.lambda.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Persons")
public class PersonItem {
    private Integer id;
    private String firstName;
    private String lastName;

    @DynamoDBHashKey(attributeName = "Id")
    public Integer getId() { return id; }
    public void setId(Integer id) {this.id = id; }

    @DynamoDBAttribute(attributeName = "First Name")
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @DynamoDBAttribute(attributeName = "Last Name")
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {this.lastName = lastName; }
}
