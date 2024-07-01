package com.hrs.checklist_resign.dto;

public class PostFinalApprovalDTO {

    private String remarks;
    private String finalApprovalStatus;

    public PostFinalApprovalDTO(String remarks, String finalApprovalStatus)
    {
        this.remarks = remarks;
        this.finalApprovalStatus = finalApprovalStatus;
    }

    //Setter and getter


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFinalApprovalStatus() {
        return finalApprovalStatus;
    }

    public void setFinalApprovalStatus(String finalApprovalStatus) {
        this.finalApprovalStatus = finalApprovalStatus;
    }
}
