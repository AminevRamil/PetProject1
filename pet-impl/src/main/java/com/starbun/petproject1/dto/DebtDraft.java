package com.starbun.petproject1.dto;

import lombok.Data;

@Data
public class DebtDraft extends DraftObject {

  private String debtor;

  private String deptSubject;

  private Boolean redemptionStatus;
}
