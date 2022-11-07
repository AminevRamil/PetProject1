package com.starbun.petproject1.dto;

import lombok.Data;

@Data
public class DebtDto {
  private Long id;

  private Long tgId;

  private String debtor;

  private String deptSubject;

  private Boolean redemptionStatus;
}
