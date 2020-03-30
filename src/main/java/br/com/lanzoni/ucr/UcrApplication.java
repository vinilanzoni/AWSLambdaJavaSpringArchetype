package br.com.lanzoni.ucr;

import br.com.lanzoni.ucr.domain.User;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Log4j2
@SpringBootApplication
public class UcrApplication {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    public static void main(String[] args) {
        SpringApplication.run(UcrApplication.class, args);
    }

    @PostConstruct
    private void init() {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        try {
            DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(User.class);
            amazonDynamoDB.deleteTable(deleteTableRequest);
        } catch (ResourceNotFoundException e) {
            log.warn("Wont delete table because it does not exists");
        }

        try {
            CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            amazonDynamoDB.createTable(createTableRequest);
        } catch (ResourceInUseException e) {
            log.warn("Wont create table because it already exists");
        }
    }
}
