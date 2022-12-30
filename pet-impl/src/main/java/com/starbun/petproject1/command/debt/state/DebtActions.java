package com.starbun.petproject1.command.debt.state;

import com.starbun.petproject1.command.CommandActions;
import com.starbun.petproject1.command.start.StartActions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Действия выполняемы в ходе работы команды /debt
 */
@RequiredArgsConstructor
public enum DebtActions implements CommandActions {
  EDIT_EXISTING(0, "EDIT_EXISTING"),
  PICK_DEBT(1, "PICK_DEBT"),
  CREATE_DEBT(2, "CREATE_DEBT"),
  SET_DEBTOR(3, "SET_DEBTOR"),
  SET_SUBJECT(4, "SET_SUBJECT"),
  SET_STATUS(5, "SET_STATUS"),
  SET_DATE(6, "SET_DATE"),
  WRONG_INPUT(7, "WRONG_INPUT"),
  ACCEPT(8, "ACCEPT"),
  CANCEL(9, "CANCEL"),
  SAVE_DEBT(10, "SAVE_DEBT"),
  SHOW_ALL(11, "SHOW_ALL" ),
  CHOSE_LEFT(12, "CHOSE_LEFT" ),
  CHOSE_RIGHT(13, "CHOSE_RIGHT" );

  @Getter
  private final Integer code;

  @Getter
  private final String name;

  public static DebtActions fromCode(Integer code) {
    for (var action : DebtActions.values()) {
      if (action.getCode().equals(code)) {
        return action;
      }
    }
    throw new IllegalArgumentException("Неверный код для действия DebtKeyboardActions: " + code);
  }
}
