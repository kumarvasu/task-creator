package com.task.agentic;

import com.task.agentic.model.AdGroupDetailDto;
import com.task.agentic.model.TaskHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextProvider {

    public String getContext(String taskType, String taskOwner){
        return "Task Knowledge: " + getTaskRelatedKnowledge(taskType)
                + "; Historical Requests: " + getTaskHistory(taskType, taskOwner)
                + "; Task Personalization configuration: " + getPersonalConfiguration(taskOwner);
    }

    public String getTaskRelatedKnowledge(String taskType) {
        List<AdGroupDetailDto> adGroupDetailDtoList = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("adGroupDetail.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                AdGroupDetailDto dto = new AdGroupDetailDto();
                String[] pairs = line.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();
                        switch (key) {
                            case "Group Name":
                                dto.setGroupName(value);
                                break;
                            case "Group Summary":
                                dto.setGroupSummary(value);
                                break;
                            case "Primary Owner":
                                dto.setPrimaryOwner(value);
                                break;
                            case "Secondary Owner":
                                dto.setSecondaryOwner(value);
                                break;
                            case "Group Classification":
                                dto.setGroupClassification(value);
                                break;
                            case "Primary Owner Supervisor":
                                dto.setPrimaryOwnerSupervisor(value);
                                break;
                            case "Primary Owner Cost Center":
                                dto.setPrimaryOwnerCostCenter(value);
                                break;
                            case "Primary Owner Office Building":
                                dto.setPrimaryOwnerOfficeBuilding(value);
                                break;
                            case "Primary Owner Phone":
                                dto.setPrimaryOwnerPhone(value);
                                break;
                            case "Primary Owner State":
                                dto.setPrimaryOwnerState(value);
                                break;
                            case "Primary Owner Country":
                                dto.setPrimaryOwnerCountry(value);
                                break;
                            // Add more fields as needed
                        }
                    }
                }
                adGroupDetailDtoList.add(dto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return adGroupDetailDtoList.toString();
    }

    //        return "REQ1: Approved; REQ2: Approved; REQ3: Approved; REQ4: Approved";

    public String getTaskHistory(String taskType, String taskOwner) {
        try {
            List<TaskHistoryDto> histories = getTaskHistories();
            StringBuilder sb = new StringBuilder();
            for (TaskHistoryDto history : histories) {
                if (history.getAdGroupName().equalsIgnoreCase(taskType)
                        && history.getAdGroupOwner().equalsIgnoreCase(taskOwner)) {
                    sb.append("TaskId: ").append(history.getTaskId())
                            .append(", Status: ").append(history.getStatus())
                            .append(", Owner: ").append(history.getAdGroupOwner())
                            .append(", Date: ").append(history.getDate())
                            .append("; ");
                }
            }
            if (sb.length() == 0) {
                return "No history found for task type: " + taskType + " and owner: " + taskOwner;
            }
            return sb.toString();
        } catch (IOException e) {
            return "No history available.";
        }
    }

    public List<TaskHistoryDto> getTaskHistories() throws IOException {
        List<TaskHistoryDto> histories = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("task_history.txt");
        if (inputStream == null) return histories;
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        String[] entries = content.toString().split(";");
        for (String entry : entries) {
            if (entry.trim().isEmpty()) continue;
            String[] fields = entry.split(",");
            Map<String, String> map = new HashMap<>();
            for (String field : fields) {
                String[] kv = field.split(":", 2);
                if (kv.length == 2) {
                    map.put(kv[0].trim(), kv[1].trim());
                }
            }
            histories.add(new TaskHistoryDto(
                    map.getOrDefault("task_id", ""),
                    map.getOrDefault("AD_GROUP_NAME", ""),
                    map.getOrDefault("AD_GROUP_OWNER_NAME", ""),
                    map.getOrDefault("task_status", ""),
                    map.getOrDefault("date", "")
            ));
        }
        return histories;
    }
    public String getPersonalConfiguration(String taskOwner){
        return "Show me my historical requests";
    }
}
