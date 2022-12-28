package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.service.InlineKeyboardService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.Collections;

import static com.starbun.petproject1.command.debt.DebtKeyboardActions.*;

/**
 * Фабрика? Абстрактная фабрика? Фабричный метод?
 */
@Component
public class DebtKeyboardService extends InlineKeyboardService {

  @Override
  public InlineKeyboardMarkup createForState(CommandStates state, Long userId) {
    DebtCommandStates debtState = (DebtCommandStates) state;
    switch (debtState) {
      case DEBT_BEGIN, DEBT_CHOSE -> {
        return debtCreateKeyboard(userId);
      }
      case DEBT_EDIT -> {
        return debtDraftKeyboard(userId);
      }
      default -> throw new NoImplementationException("Для данного состояния не существует инлайн клавиатуры: " + state);
    }
  }

  /**
   * Создание клавиатуры под сообщением с драфтом долга
   */
  private InlineKeyboardMarkup debtDraftKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Arrays.asList(
            createButton("Указать должника", SET_DEBTOR, userId),
            createButton("Указать предмет долга", SET_SUBJECT, userId)))
        .keyboardRow(Arrays.asList(
            createButton("Указать статус погашения", SET_STATUS, userId),
            createButton("Указать дату возврата (опц.)", SET_DATE, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Сохранить", SAVE_DEBT, userId)))
        .build();
  }

  /**
   * Создание клавиатуры под сообщением с предложением создания нового долга
   */
  private InlineKeyboardMarkup debtCreateKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Collections.singletonList(
            createButton("Да", CREATE_DEBT, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Просмотр долгов", SHOW_ALL, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Редактировать долг", EDIT_EXISTING, userId)))
        .build();
  }
}
