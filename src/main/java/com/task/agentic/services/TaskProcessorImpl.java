package com.task.agentic.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.agentic.TaskCreator;
import com.task.agentic.model.TaskDetailsDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessorImpl implements TaskProcessor {

	@Autowired
    ChatClient.Builder chatClientBuilder;
    @Override
    public String process(TaskDetailsDTO taskInfo) throws JsonProcessingException {
        System.out.println("Task Input : " + taskInfo);
        var chatClient = chatClientBuilder.build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(taskInfo);
        String response = new TaskCreator(chatClient).createTask("""
					<user input>
					%s
					</user input>
					""".formatted(json));
        return taskInfo.toString();
    }


}
