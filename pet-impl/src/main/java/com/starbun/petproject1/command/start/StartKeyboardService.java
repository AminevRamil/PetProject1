package com.starbun.petproject1.command.start;

import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.service.InlineKeyboardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

import static com.starbun.petproject1.command.start.StartActions.RESTART;
import static com.starbun.petproject1.command.start.StartActions.SHOW_COMMANDS;

@Component
@AllArgsConstructor
public class StartKeyboardService implements InlineKeyboardService<StartState, StartActions> {
  @Override
  public InlineKeyboardMarkup createForState(StartState state, Long userId) {
    switch (state) {
      case START_BEGIN -> {
        return startKeyboard(userId);
      }
      default -> throw new NoImplementationException("Для данного состояния не существует инлайн клавиатуры: " + state);
    }
  }

  @Override
  public InlineKeyboardMarkup createForAction(StartActions action, Long userId) {
    switch (action) {
      case SHOW_COMMANDS -> {
        return startKeyboard(userId);
      }
      default -> throw new NoImplementationException("Для данного действия не существует инлайн клавиатуры: " + action);
    }
  }

  /**
   * Создание стартовой клавиатуры.
   */
  private InlineKeyboardMarkup startKeyboard(Long userId) {
    return InlineKeyboardMarkup.builder()
        .keyboardRow(Arrays.asList(
            createButton("Показать список команд", SHOW_COMMANDS, userId)))
        .keyboardRow(Arrays.asList(
            createButton("Ре-стартуем!", RESTART, userId)))
        .build();
  }
}
