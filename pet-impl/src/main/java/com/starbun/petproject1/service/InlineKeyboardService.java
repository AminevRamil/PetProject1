package com.starbun.petproject1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.dto.ButtonAction;
import com.starbun.petproject1.dto.InlineButtonInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class InlineKeyboardService {

  protected ObjectMapper jsonMapper;

  @Autowired
  public void setJsonMapper(ObjectMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  /**
   * Создание клавиатуры для указанного состояния
   *
   * @param state  состояние для которого нужна клавиатура
   * @param userId пользователь, который будет "владельцем клавиатуры"
   * @return настроенную клавиатуру для вставки в сообщение
   */
  abstract public InlineKeyboardMarkup createForState(CommandStates state, Long userId);

  /**
   * Метод для создания инлайн кнопки
   *
   * @param buttonAction действие, которое будет выполнять кнопка
   * @param userId       идентификатор пользователя телеграм, с которым будет связана кнопка
   * @param text         текст кнопки, видимый пользователем
   * @return кнопку, готовую для вставки в InlineKeyboardMarkup
   */
  @SneakyThrows
  protected InlineKeyboardButton createButton(ButtonAction buttonAction, Long userId, String text) {
    InlineButtonInfo info = InlineButtonInfo.builder()
        .bAction(buttonAction)
        .uId(userId)
        .build();
    return InlineKeyboardButton.builder()
        .text(text)
        .callbackData(jsonMapper.writeValueAsString(info))
        .build();
  }
}
