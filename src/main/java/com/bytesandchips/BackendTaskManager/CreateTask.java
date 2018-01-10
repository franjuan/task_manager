package com.bytesandchips.BackendTaskManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.bytesandchips.BackendTaskManager.CreateTask.TaskRequest;
import com.bytesandchips.BackendTaskManager.Entities.Task;

public class CreateTask extends CommonTask<TaskRequest, Task> {
	// Initialize the Log4j logger.
	static final Logger logger = LogManager.getLogger(CreateTask.class);

	public static class TaskRequest {
		public String name;
		public TaskRequest() {
		}
	}

	public CreateTask() {
		logger.info("Initializing Create Task Lambda instance");
	}

	protected Task processRequest(DynamoDBMapper mapper, TaskRequest input) {
		Task task = new Task();
		task.setName(input.name);

		try {
			mapper.save(task);
			logger.debug("Task created correctly: {}", task);
			return task;
		} catch (ConditionalCheckFailedException e) {
			logger.error("Error creating task", e);
			return null;
		}
	}
}
