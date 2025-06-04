package com.task.agentic;

public class Response {
    private String approver;
    private String cardJson;

    public String getApprover() {
        return approver;
    }

    public String getCardJson() {
        return cardJson;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public void setCardJson(String cardJson) {
        this.cardJson = cardJson;
    }

    @Override
    public String toString() {
        return "Response{" +
                "approver='" + approver + '\'' +
                ", cardJson='" + cardJson + '\'' +
                '}';
    }
}
