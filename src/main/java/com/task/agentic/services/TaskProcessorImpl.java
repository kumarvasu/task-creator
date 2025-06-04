package com.task.agentic.services;

import com.task.agentic.model.AdGroupDetailDto;
import com.task.agentic.model.TaskDetailsDTO;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskProcessorImpl implements TaskProcessor {

    @Override
    public String process(TaskDetailsDTO taskInfo){
        System.out.println("Task Input : " + taskInfo);
        return taskInfo.toString();
    }
}