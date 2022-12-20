package com.starbun.petproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект с ответом команды. Ответ может иметь разные типы и хранить разную информацию.
 * TODO Добавить один единый обработчик ответов от команд, чтоб команды не занимались рутиной Telegram API.
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