package com.starbun.petproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Информация передаваемая из инлайн-кнопок. Ограничена 64 байтами
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InlineButtonInfo {
  /**
   * Идентификатор пользователя в телеграм,
   * который спровоцировал появление кнопки (владелец кнопки?)
   */
  private Long userId;

  /**
   * Код кнопки
   */
  private Integer keyboardActionCode;

  public InlineButtonInfo(String argsInOneLine) {
    String[] s = argsInOneLine.split(" ");
    this.userId = Long.valueOf(s[0]);
    this.keyboardActionCode = Integer.valueOf(s[1]);
  }

  public String intoButtonData() {
    return String.format("%d %d", userId, keyboardActionCode);
  }
}
