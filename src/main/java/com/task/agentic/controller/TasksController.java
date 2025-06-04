package com.task.agentic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task.agentic.model.TaskDetailsDTO;
import com.task.agentic.services.TaskProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/taskExecutor")
public class TasksController {

    @Autowired
    TaskProcessor taskProcessor;

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public ResponseEntity<String> taskExecutor(@RequestBody @NotNull TaskDetailsDTO taskDetails) throws JsonProcessingException {

        taskProcessor.process(taskDetails);
        return new ResponseEntity<>("Executed..!", HttpStatus.OK);
    }

}
