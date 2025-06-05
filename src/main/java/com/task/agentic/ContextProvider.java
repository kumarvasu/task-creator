package com.task.agentic;

import com.task.agentic.model.AdGroupDetailDto;
import com.task.agentic.model.TaskHistoryDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextProvider {

    public static final String GROUP_NAME = "Group Name";
    public static final String GROUP_SUMMARY = "Group Summary";
    public static final String PRIMARY_OWNER = "Primary Owner";
    public static final String SECONDARY_OWNER = "Secondary Owner";
    public static final String GROUP_CLASSIFICATION = "Group Classification";
    public static final String PRIMARY_OWNER_SUPERVISOR = "Primary Owner Supervisor";
    public static final String PRIMARY_OWNER_COST_CENTER = "Primary Owner Cost Center";
    public static final String PRIMARY_OWNER_OFFICE_BUILDING = "Primary Owner Office Building";
    public static final String PRIMARY_OWNER_PHONE = "Primary Owner Phone";
    public static final String PRIMARY_OWNER_STATE = "Primary Owner State";
    public static final String PRIMARY_OWNER_COUNTRY = "Primary Owner Country";

    public String getContext(String taskOwner, String adGroupName) {
        return "--- Task Knowledge ---\n" + getTaskRelatedKnowledge(adGroupName) + "\n\n"
                + "--- Task Histories ---\n" + getTaskHistory(adGroupName) + "\n\n"
                + "--- Task Personalization Configuration ---\n" + getPersonalConfiguration(taskOwner);
    }


    public String getTaskRelatedKnowledge(String adGroupName) {
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
                            case GROUP_NAME:
                                dto.setGroupName(value);
                                break;
                            case GROUP_SUMMARY:
                                dto.setGroupSummary(value);
                                break;
                            case PRIMARY_OWNER:
                                dto.setPrimaryOwner(value);
                                break;
                            case SECONDARY_OWNER:
                                dto.setSecondaryOwner(value);
                                break;
                            case GROUP_CLASSIFICATION:
                                dto.setGroupClassification(value);
                                break;
                            case PRIMARY_OWNER_SUPERVISOR:
                                dto.setPrimaryOwnerSupervisor(value);
                                break;
                            case PRIMARY_OWNER_COST_CENTER:
                                dto.setPrimaryOwnerCostCenter(value);
                                break;
                            case PRIMARY_OWNER_OFFICE_BUILDING:
                                dto.setPrimaryOwnerOfficeBuilding(value);
                                break;
                            case PRIMARY_OWNER_PHONE:
                                dto.setPrimaryOwnerPhone(value);
                                break;
                            case PRIMARY_OWNER_STATE:
                                dto.setPrimaryOwnerState(value);
                                break;
                            case PRIMARY_OWNER_COUNTRY:
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
        List<AdGroupDetailDto> result = adGroupDetailDtoList.stream().filter(dto -> adGroupName.equals(dto.getGroupName())).toList();
        StringBuilder sb = new StringBuilder();
        if (!result.isEmpty()) {
            result.forEach(dto -> sb.append(GROUP_NAME).append(": ").append(dto.getGroupName())
                    .append(", ").append(GROUP_SUMMARY).append(": ").append(dto.getGroupSummary())
                    .append(", ").append(PRIMARY_OWNER).append(": ").append(dto.getPrimaryOwner())
                    .append(", ").append(SECONDARY_OWNER).append(": ").append(dto.getSecondaryOwner())
                    .append(", ").append(GROUP_CLASSIFICATION).append(": ").append(dto.getGroupClassification())
                    .append(", ").append(PRIMARY_OWNER_SUPERVISOR).append(": ").append(dto.getPrimaryOwnerSupervisor())
                    .append(", ").append(PRIMARY_OWNER_COST_CENTER).append(": ").append(dto.getPrimaryOwnerCostCenter())
                    .append(", ").append(PRIMARY_OWNER_OFFICE_BUILDING).append(": ").append(dto.getPrimaryOwnerOfficeBuilding())
                    .append(", ").append(PRIMARY_OWNER_PHONE).append(": ").append(dto.getPrimaryOwnerPhone())
                    .append(", ").append(PRIMARY_OWNER_STATE).append(": ").append(dto.getPrimaryOwnerState())
                    .append(", ").append(PRIMARY_OWNER_COUNTRY).append(": ").append(dto.getPrimaryOwnerCountry()).append("; "));

        }
        return sb.toString();
    }

//        return "REQ1: Approved; REQ2: Approved; REQ3: Approved; REQ4: Approved";

    public String getTaskHistory(String adGroupName) {
        try {
            List<TaskHistoryDto> histories = getTaskHistories();
            StringBuilder sb = new StringBuilder();
            for (TaskHistoryDto history : histories) {
                if (history.getAdGroupName().equalsIgnoreCase(adGroupName)) {
                    sb.append("TaskId: ").append(history.getTaskId())
                            .append(", AD Group Name: ").append(history.getAdGroupName())
                            .append(", Status: ").append(history.getStatus())
                            .append(", Owner: ").append(history.getAdGroupOwner())
                            .append(", Date: ").append(history.getDate())
                            .append("; ");
                }
            }
            if (sb.length() == 0) {
                return "No history found for AD Group Name: " + adGroupName;
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
        if ("BH01126630@devcorptenant.com".equalsIgnoreCase(taskOwner)) {
            return "Detailed configuration: Show last three historical requests, preferences, and Key Information. If the taskOwner does not have three historical requests, show all availble historical requests.";
        } else if ("BH01483157@devcorptenant.com".equalsIgnoreCase(taskOwner)){
            return "Show Title and Summary only.";
        }
        return "";
    }
}
