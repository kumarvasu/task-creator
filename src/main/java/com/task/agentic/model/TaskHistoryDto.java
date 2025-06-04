package com.task.agentic.model;

import lombok.Data;

@Data
public class TaskHistoryDto {
    private String taskId;
    private String adGroupName;
    private String adGroupOwner;
    private String status;
    private String date;

    public TaskHistoryDto() {
    }

    public TaskHistoryDto(String taskId, String adGroupName, String adGroupOwner, String status, String date) {
        this.taskId = taskId;
        this.adGroupName = adGroupName;
        this.adGroupOwner = adGroupOwner;
        this.status = status;
        this.date = date;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAdGroupName() {
        return adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
        this.adGroupName = adGroupName;
    }

    public String getAdGroupOwner() {
        return adGroupOwner;
    }

    public void setAdGroupOwner(String adGroupOwner) {
        this.adGroupOwner = adGroupOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
