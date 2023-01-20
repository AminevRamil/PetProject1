package com.starbun.petproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект с ответом команды. Ответ может иметь разные типы и хранить разную информацию.
 * Подразумевается, что это декларативное описание того, что нужно сделать по результатам работы команды.
 *
 * @see com.starbun.petproject1.service.CommandResponseResolver
 */
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