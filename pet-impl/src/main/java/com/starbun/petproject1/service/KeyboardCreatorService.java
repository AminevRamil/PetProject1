package com.starbun.petproject1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbun.petproject1.dto.ButtonAction;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.dto.State;
import com.starbun.petproject1.exception.NoImplementationException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.Collections;

/**
 * Фабрика? Абстрактная фабрика? Фабричный метод?
 */
@Component
@AllArgsConstructor
public class KeyboardCreatorService {

  private final ObjectMapper jsonMapper;

  public InlineKeyboardMarkup createInlineKeyboardForState(State state, Long userId) {
    switch (state) {
      case DEBT_CREATE -> {
        return debtCreateKeyboard(userId);
      }
      case DEBT_DRAFT -> {
        return debtDraftKeyboard(userId);
      }
      default -> throw new NoImplementationException("Для данного состояния не существует инлайн клавиатуры: " + state);
    }
  }

  /**
   * Создание клавиатуры под сообщением с драфтом долга
   *
   * @param userId идентификатор пользователя телеграм для которого создаётся клавиатура
   * @return готовую инлайн-клавиатуру
   */
  private InlineKeyboardMarkup debtDraftKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Arrays.asList(
            createButton(ButtonAction.CHANGE_DEBTOR, userId, "Указать должника"),
            createButton(ButtonAction.CHANGE_DEBT_SUBJECT, userId, "Указать предмет долга")))
        .keyboardRow(Arrays.asList(
            createButton(ButtonAction.CHANGE_DEBT_STATUS, userId, "Указать статус погашения"),
            createButton(ButtonAction.CHANGE_DATE, userId, "Указать дату возврата (опц.)")))
        .keyboardRow(Collections.singletonList(
            createButton(ButtonAction.SAVE_DEBT, userId, "Сохранить")))
        .build();
  }

  /**
   * Создание клавиатуры под сообщением с предложением создания нового долга
   *
   * @param userId идентификатор пользователя телеграм для которого создаётся клавиатура
   * @return готовую инлайн-клавиатуру
   */
  private InlineKeyboardMarkup debtCreateKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Collections.singletonList(
            createButton(ButtonAction.CREATE_DEBT, userId, "Да")))
        .keyboardRow(Collections.singletonList(
            createButton(ButtonAction.SELECT_DEBT, userId, "Просмотр долгов")))
        .keyboardRow(Collections.singletonList(
            createButton(ButtonAction.SELECT_DEBT, userId, "Редактировать долг")))
        .build();
  }

  /**
   * Метод для создания инлайн кнопки
   * @param buttonAction действие, которое будет выполнять кнопка
   * @param userId идентификатор пользователя телеграм, с которым будет связана кнопка
   * @param text текст кнопки, видимый пользователем
   * @return кнопку, готовую для вставки в InlineKeyboardMarkup
   */
  @SneakyThrows
  private InlineKeyboardButton createButton(ButtonAction buttonAction, Long userId, String text) {
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
