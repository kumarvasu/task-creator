package com.task.agentic.services;

import org.springframework.stereotype.Service;

@Service
public class TaskProcessorImpl implements TaskProcessor {

    @Override
    public String process(String taskInfo){
        return taskInfo;
    }


}
