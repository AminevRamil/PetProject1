package com.starbun.petproject1.service;

import com.starbun.petproject1.command.CommandStates;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InlineKeyboardService {

  /**
   * Создание клавиатуры для указанного состояния
   * @param state состояние для которого нужна клавиатура
   * @param userId пользователь, который будет "владельцем клавиатуры"
   * @return настроенную клавиатуру для вставки в сообщение
   */
  InlineKeyboardMarkup createForState(CommandStates state, Long userId);
}
