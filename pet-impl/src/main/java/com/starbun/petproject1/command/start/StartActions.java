package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.CommandActions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Действия выполняемы в ходе работы команды /start
 */
@RequiredArgsConstructor
public enum StartActions implements CommandActions {
  RESTART(0, "RESTART"),
  SHOW_MENU(1, "SHOW_MENU");

  @Getter
  private final Integer code;

  @Getter
  private final String name;

  public static StartActions fromCode(Integer code) {
    for (var action : StartActions.values()) {
      if (action.getCode().equals(code)) {
        return action;
      }
    }
    throw new IllegalArgumentException("Неверный код для действия DebtKeyboardActions: " + code);
  }
}
