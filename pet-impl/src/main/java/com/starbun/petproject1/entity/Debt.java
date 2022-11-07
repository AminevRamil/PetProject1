package com.starbun.petproject1.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "DEBTS")
public class Debt {
  @Id
  private Long id;

  @Column(name = "TG_ID")
  private Long tgId;

  @Column(name = "DEBTOR")
  private String debtor;

  @Column(name = "DEBT_SUBJECT")
  private String deptSubject;

  @Column(name = "REDEMPTION_STATUS")
  private Boolean redemptionStatus;
}
