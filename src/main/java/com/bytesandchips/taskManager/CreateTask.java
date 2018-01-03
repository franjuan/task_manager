package com.bytesandchips.taskManager;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bytesandchips.taskManager.CreateTask.TaskOperation;
import com.bytesandchips.taskManager.CreateTask.TaskRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateTask implements RequestHandler<TaskRequest, TaskOperation> {
	// Initialize the Log4j logger.
    static final Logger logger = LogManager.getLogger(CreateTask.class);
    
	public static class TaskOperation {
		public TaskOperation() {
			// TODO Auto-generated constructor stub
		}
	}

	public static class TaskRequest {
		public TaskRequest() {
			// TODO Auto-generated constructor stub
		}
	}

	public CreateTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TaskOperation handleRequest(TaskRequest request, Context context) {
		logger.info("By log");
		return new TaskOperation();
	}

}
