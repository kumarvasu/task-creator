package com.task.agentic.services;

import com.task.agentic.model.TaskDetailsDTO;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessorImpl implements TaskProcessor {

    @Override
    public String process(TaskDetailsDTO taskInfo){
        System.out.println("Task Input : " + taskInfo);
        return taskInfo.toString();
    }


}
