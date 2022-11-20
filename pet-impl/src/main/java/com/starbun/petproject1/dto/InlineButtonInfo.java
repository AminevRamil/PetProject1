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
   * Идентификатор пользователя в телеграм
   */
  private Long uId;
  /**
   * Тип кнопки
   */
  private ButtonAction bAction;
}
