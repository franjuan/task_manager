package com.bytesandchips.BackendTaskManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.bytesandchips.BackendTaskManager.GetTask.TaskCriteria;
import com.bytesandchips.BackendTaskManager.Entities.Task;

public class GetTask extends CommonTask<TaskCriteria, Task> {
	// Initialize the Log4j logger.
	static final Logger logger = LogManager.getLogger(GetTask.class);

	public static class TaskCriteria {
		public String id;

		public TaskCriteria() {
		}
	}

	public GetTask() {
		logger.info("Initializing Get Task Lambda instance");
	}

	protected Task processRequest(DynamoDBMapper mapper, TaskCriteria input) {
		logger.trace("Searching for task with id {}", input.id);
		Task task = mapper.load(Task.class, input.id);
		if (task != null) {
			logger.debug("Task retrieved correctly: {}", task);
			return task;
		} else {
			logger.warn("Task not found with id {}", input.id);
			return null;
		}
	}
}
