package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.KeyboardAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DebtKeyboardActions implements KeyboardAction {
  EDIT_EXISTING(0, "EDIT_EXISTING"),
  PICK_DEBT(1, "PICK_DEBT"),
  CREATE_DEBT(2, "CREATE_DEBT"),
  SET_DEBTOR(3, "SET_A"),
  SET_SUBJECT(4, "SET_B"),
  SET_STATUS(5, "SET_C"),
  SET_DATE(6, "SET_C"),
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

  public static DebtKeyboardActions fromCode(Integer code) {
    for (var action : DebtKeyboardActions.values()) {
      if (action.getCode().equals(code)) {
        return action;
      }
    }
    throw new IllegalArgumentException("Неверный код для действия DebtKeyboardActions: " + code);
  }
}
