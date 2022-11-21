package com.starbun.petproject1.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class DebtDraft extends DraftObject {

  private String debtor;

  private String deptSubject;

  private LocalDate debtDate;

  private Boolean redemptionStatus = false;
}
