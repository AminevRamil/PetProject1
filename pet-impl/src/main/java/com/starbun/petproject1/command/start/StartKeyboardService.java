package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.dto.ButtonAction;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.service.InlineKeyboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class StartKeyboardService extends InlineKeyboardService {
  @Override
  public InlineKeyboardMarkup createForState(CommandStates state, Long userId) {
    StartCommandStates startState = (StartCommandStates) state;
    switch (startState) {
      case START_OPTIONS -> {
        return startKeyboard(userId);
      }
      default -> throw new NoImplementationException("Для данного состояния не существует инлайн клавиатуры: " + state);
    }
  }

  /**
   * Создание стартовой клавиатуры.
   */
  private InlineKeyboardMarkup startKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Arrays.asList(
            createButton(ButtonAction.SHOW_MENU, userId, "Показать меню")))
        .keyboardRow(Arrays.asList(
            createButton(ButtonAction.RESTART, userId, "Ре-стартуем!")))
        .build();
  }
}
