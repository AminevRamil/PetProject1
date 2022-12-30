package com.starbun.petproject1.service;

import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.command.CommandActions;
import com.starbun.petproject1.dto.InlineButtonInfo;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class InlineKeyboardService<S extends CommandStates<A>, A extends CommandActions> {

  /**
   * Создание клавиатуры для указанного состояния
   *
   * @param state  состояние для которого нужна клавиатура
   * @param userId идентификатор пользователя телеграм, с которым будет связана создаваемая клавиатура
   * @return настроенную клавиатуру для вставки в сообщение
   */
  abstract public InlineKeyboardMarkup createForState(S state, Long userId);

  /**
   * Метод для создания инлайн кнопки
   *
   * @param text   текст кнопки, видимый пользователем
   * @param action действие, которое будет выполнять кнопка
   * @param userId идентификатор пользователя телеграм, с которым будет связана кнопка
   * @return кнопку, готовую для вставки в InlineKeyboardMarkup
   */
  @SneakyThrows
  protected InlineKeyboardButton createButton(String text, A action, Long userId) {
    InlineButtonInfo buttonInfo = InlineButtonInfo.builder()
        .userId(userId)
        .keyboardActionCode(action.getCode())
        .build();
    return InlineKeyboardButton.builder()
        .text(text)
        .callbackData(buttonInfo.intoButtonData())
        .build();
  }
}
