package com.task.agentic.controller;

import com.task.agentic.services.TaskProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taskExecutor")
public class TasksController {

    TaskProcessor taskProcessor;

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> taskExecutor(){

        String certTask = taskDetails();
        return new ResponseEntity<>(taskProcessor.process(certTask), HttpStatus.OK);
    }

    public static String taskDetails(){

        return "[{\n" +
                "\t\"taskId\" : \"abcd1234\",\n" +
                "\t\"adGroupId\" : \"ADGRP12345\",\n" +
                "\t\"brid\" : \"G01253654\"\n" +
                " }]";
    }


}
