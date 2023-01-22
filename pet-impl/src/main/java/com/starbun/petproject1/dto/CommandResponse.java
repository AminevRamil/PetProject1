package com.starbun.petproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

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
  private CommandResponseType commandResponseType;
  private Integer replyToMessageId;
  private String messageText;
  private Long messageId;
  private Long chatId;
  private Long userId;
  private ReplyKeyboard replyMarkup;
}

