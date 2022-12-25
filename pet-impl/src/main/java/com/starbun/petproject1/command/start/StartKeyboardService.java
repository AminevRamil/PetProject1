package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.service.InlineKeyboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

import static com.starbun.petproject1.command.start.StartKeyboardActions.RESTART;
import static com.starbun.petproject1.command.start.StartKeyboardActions.SHOW_MENU;

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
            createButton("Показать меню", SHOW_MENU, userId)))
        .keyboardRow(Arrays.asList(
            createButton("Ре-стартуем!", RESTART, userId)))
        .build();
  }
}
