package com.starbun.petproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommandResponse {
  private ResponseType responseType;
  private String messageText;
  private Long messageId;
  private String ChatId;
  private Long userId;
}

enum ResponseType{
  NEW_MESSAGE,
  ALTER_MESSAGE
}