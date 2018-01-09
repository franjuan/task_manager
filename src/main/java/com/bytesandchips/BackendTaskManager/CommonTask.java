package com.bytesandchips.BackendTaskManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.Builder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Strings;

public abstract class CommonTask<Request, Response> implements RequestHandler<Request, Response> {
	private static String tableNameReplacement = System.getenv("TASKS_TABLE_NAME");
	private static final Logger logger = LogManager.getLogger(CommonTask.class);
	
	public CommonTask() {
		
	}
	
	@Override
	public Response handleRequest(Request input, Context context) {
		logger.trace("Connecting to DynamoDB");
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
		Builder mapperConfigBuilder = DynamoDBMapperConfig.builder();
		mapperConfigBuilder.setSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE);
		mapperConfigBuilder.setConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		mapperConfigBuilder.setPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING);
		if (!Strings.isNullOrEmpty(tableNameReplacement)) {
			mapperConfigBuilder.setTableNameOverride(
					DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableNameReplacement));
		}
		DynamoDBMapper mapper = new DynamoDBMapper(client, mapperConfigBuilder.build());
		logger.trace("DynamoDB mapper created");
		
		return processRequest(mapper, input);
	}

	protected abstract Response processRequest(DynamoDBMapper mapper, Request input);
}
