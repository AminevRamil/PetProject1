package com.starbun.petproject1.command.debt.state;

import com.starbun.petproject1.command.CommandStates;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Все состояния работы с пользователем, в которых может пребывать данная команда
 */
@Getter
public enum DebtState implements CommandStates<DebtActions> {
  DEBT_BEGIN(0, "DEBT_BEGIN", DebtActions.EDIT_EXISTING, DebtActions.CREATE_DEBT, DebtActions.SHOW_ALL),
  DEBT_CHOSE(1, "DEBT_CHOSE", DebtActions.PICK_DEBT, DebtActions.CHOSE_LEFT, DebtActions.CHOSE_RIGHT),
  DEBT_EDIT(2, "DEBT_EDIT", DebtActions.SAVE_DEBT, DebtActions.CANCEL, DebtActions.SET_DEBTOR, DebtActions.SET_SUBJECT, DebtActions.SET_STATUS),
  DEBT_INPUT(3, "DEBT_INPUT", DebtActions.WRONG_INPUT, DebtActions.ACCEPT);
  private final Integer code;
  private final String name;
  private final List<DebtActions> applicableActions;

  DebtState(Integer code, String name, DebtActions... actions) {
    this.code = code;
    this.name = name;
    this.applicableActions = Arrays.stream(actions).toList();
  }
}
