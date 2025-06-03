package com.task.agentic;

public class ContextProvider {

    public String getContext(String taskType, String taskOwner){
        return "Task Knowledge: " + getTaskRelatedKnowledge(taskType)
                + "; Historical Requests: " + getTaskHistory(taskType, taskOwner)
                + "; Task Personalization configuration: " + getPersonalConfiguration(taskOwner);
    }

    public String getTaskRelatedKnowledge(String taskType){
        return "One cert task validates the ad group owner is still active and the correct owner is still associated with the AD group.";
    }

    public String getTaskHistory(String taskType, String taskOwner){
        return "REQ1: Rejected; REQ2: Rejected; REQ3: Rejected; REQ4: Rejected";
    }

    public String getPersonalConfiguration(String taskOwner){
        return "Show me my historical requests";
    }
}
