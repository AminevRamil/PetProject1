package com.starbun.petproject1.entity;

import com.starbun.petproject1.dto.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER_STATE")
public class UserState {
  @Id
  @Column
  private Long tgId;

  @Column
  @Enumerated(EnumType.STRING)
  private State currentState;

  @Column
  private Object stateMemo;
}
