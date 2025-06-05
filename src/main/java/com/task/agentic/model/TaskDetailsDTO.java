package com.task.agentic.model;

import org.springframework.stereotype.Component;

@Component
public class TaskDetailsDTO {

    Long task_id;
    String approver_name;
    String approver_email;
    String requestor_name;
    String task_type;
    String task_details;
    String adGroup;
    String owner;
    String task_creation_date;
    String due_date;


    public void setApprover_email(String approver_email) {
        this.approver_email = approver_email;
    }

    public String getApprover_email() {
        return approver_email;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public String getApprover_name() {
        return approver_name;
    }

    public void setApprover_name(String approver_name) {
        this.approver_name = approver_name;
    }

    public String getRequestor_name() {
        return requestor_name;
    }

    public void setRequestor_name(String requestor_name) {
        this.requestor_name = requestor_name;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTask_details() {
        return task_details;
    }

    public void setTask_details(String task_details) {
        this.task_details = task_details;
    }

    public String getAdGroup() {
        return adGroup;
    }

    public void setAdGroup(String adGroup) {
        this.adGroup = adGroup;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTask_creation_date() {
        return task_creation_date;
    }

    public void setTask_creation_date(String task_creation_date) {
        this.task_creation_date = task_creation_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    @Override
    public String toString() {
        return "TaskDetailsDTO{" +
                "task_id=" + task_id +
                ", approver_name='" + approver_name + '\'' +
                ", approver_email='" + approver_email + '\'' +
                ", requestor_name='" + requestor_name + '\'' +
                ", task_type='" + task_type + '\'' +
                ", task_details='" + task_details + '\'' +
                ", adGroup='" + adGroup + '\'' +
                ", owner='" + owner + '\'' +
                ", task_creation_date='" + task_creation_date + '\'' +
                ", due_date='" + due_date + '\'' +
                '}';
    }
}
