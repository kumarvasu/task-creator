package com.task.agentic.services;

import com.task.agentic.model.TaskDetailsDTO;
import com.task.agentic.dto.AdGroupDetailDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class TaskProcessorImpl implements TaskProcessor {

    @Override
    public String process(TaskDetailsDTO taskInfo){
        System.out.println("Task Input : " + taskInfo);
        return taskInfo.toString();
    }

    private AdGroupDetailDto readAdGroupDetail() {
        AdGroupDetailDto dto = new AdGroupDetailDto();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("adGroupDetail.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    if ("groupId".equalsIgnoreCase(parts[0].trim())) {
                        dto.setGroupId(parts[1].trim());
                    } else if ("groupName".equalsIgnoreCase(parts[0].trim())) {
                        dto.setGroupName(parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            // Handle exception as needed
            e.printStackTrace();
        }
        return dto;
    }
}