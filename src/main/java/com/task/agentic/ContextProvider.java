package com.task.agentic;

import com.task.agentic.model.AdGroupDetailDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContextProvider {

    public String getContext(String taskType, String taskOwner) {
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

    public String getTaskHistory(String taskType, String taskOwner) {
        return "REQ1: Rejected; REQ2: Rejected; REQ3: Rejected; REQ4: Rejected";
    }

    public String getPersonalConfiguration(String taskOwner) {
        return "Show me my historical requests";
    }
}
