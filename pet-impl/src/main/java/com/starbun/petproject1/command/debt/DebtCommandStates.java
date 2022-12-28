package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.CommandStates;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Все состояния работы с пользователем, в которых может пребывать данная команда
 */
@Getter
public enum DebtCommandStates implements CommandStates {
  DEBT_BEGIN(0, "DEBT_BEGIN", DebtKeyboardActions.EDIT_EXISTING, DebtKeyboardActions.CREATE_DEBT, DebtKeyboardActions.SHOW_ALL),
  DEBT_CHOSE(1, "DEBT_CHOSE", DebtKeyboardActions.PICK_DEBT, DebtKeyboardActions.CHOSE_LEFT, DebtKeyboardActions.CHOSE_RIGHT),
  DEBT_EDIT(2, "DEBT_EDIT", DebtKeyboardActions.SAVE_DEBT, DebtKeyboardActions.CANCEL, DebtKeyboardActions.SET_DEBTOR, DebtKeyboardActions.SET_SUBJECT, DebtKeyboardActions.SET_STATUS),
  DEBT_INPUT(3, "DEBT_INPUT", DebtKeyboardActions.WRONG_INPUT, DebtKeyboardActions.ACCEPT);
  private final Integer code;
  private final String name;
  private final List<DebtKeyboardActions> applicableActions;

  DebtCommandStates(Integer code, String name, DebtKeyboardActions... actions) {
    this.code = code;
    this.name = name;
    this.applicableActions = Arrays.stream(actions).toList();
  }
}
