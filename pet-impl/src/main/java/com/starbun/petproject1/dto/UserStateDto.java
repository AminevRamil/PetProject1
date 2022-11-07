package com.starbun.petproject1.dto;

import lombok.Data;

@Data
public class UserStateDto {
  private Long tgId;

  private State currentState;

  private Object stateMemo;
}
