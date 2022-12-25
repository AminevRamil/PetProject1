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
      case DEBT_OPTIONS, DEBT_CREATE -> {
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
   */
  private InlineKeyboardMarkup debtDraftKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Arrays.asList(
            createButton("Указать должника", DEBT_SET_DEBTOR, userId),
            createButton("Указать предмет долга", DEBT_SET_SUBJECT, userId)))
        .keyboardRow(Arrays.asList(
            createButton("Указать статус погашения", DEBT_SET_STATUS, userId),
            createButton("Указать дату возврата (опц.)", DEBT_SET_DATE, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Сохранить", DEBT_SAVE, userId)))
        .build();
  }

  /**
   * Создание клавиатуры под сообщением с предложением создания нового долга
   */
  private InlineKeyboardMarkup debtCreateKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Collections.singletonList(
            createButton("Да", DEBT_CREATE, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Просмотр долгов", DEBT_SHOW_ALL, userId)))
        .keyboardRow(Collections.singletonList(
            createButton("Редактировать долг", DEBT_CHANGE, userId)))
        .build();
  }
}
