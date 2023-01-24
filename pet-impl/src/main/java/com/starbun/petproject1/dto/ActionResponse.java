package com.starbun.petproject1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionResponse {
  private CommandResponseType responseType;
  private String messageText;
}
