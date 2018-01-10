package com.bytesandchips.BackendTaskManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.bytesandchips.BackendTaskManager.RemoveTask.TaskCriteria;
import com.bytesandchips.BackendTaskManager.Entities.Task;

public class RemoveTask extends CommonTask<TaskCriteria, Task> {
	// Initialize the Log4j logger.
	static final Logger logger = LogManager.getLogger(RemoveTask.class);

	public static class TaskCriteria {
		public String id;

		public TaskCriteria() {
		}
	}

	public RemoveTask() {
		logger.info("Initializing Remove Task Lambda instance");
	}

	protected Task processRequest(DynamoDBMapper mapper, TaskCriteria input) {
		logger.trace("Searching for task with id {}", input.id);
		Task task = mapper.load(Task.class, input.id);
		if (task != null) {
			logger.trace("Task retrieved correctly: {}", task);
			try {
				mapper.delete(task);
				logger.debug("Task removed correctly: {}", task);
				return task;
			} catch (ConditionalCheckFailedException e) {
				logger.error("Error removing task", e);
				return task;
			}
		} else {
			logger.warn("Task not found with id {}", input.id);
			return null;
		}
	}
}
