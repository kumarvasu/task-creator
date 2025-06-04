package com.task.agentic.services;

import com.task.agentic.model.TaskDetailsDTO;

public interface TaskProcessor {

    String process(TaskDetailsDTO taskInfo);
}
