package com.task.agentic.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task.agentic.model.TaskDetailsDTO;

public interface TaskProcessor {

    String process(TaskDetailsDTO taskInfo) throws JsonProcessingException;
}
