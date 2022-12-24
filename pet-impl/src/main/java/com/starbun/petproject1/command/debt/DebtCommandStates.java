package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.CommandStates;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Все состояния работы с пользователем, в которых может пребывать данная команда
 */
@Getter
@AllArgsConstructor
public enum DebtCommandStates implements CommandStates {
  DEBT_OPTIONS(0, "DEBT_OPTIONS"),
  DEBT_CREATE(1, "DEBT_CREATE"),
  DEBT_DRAFT(2, "DEBT_CREATE"),
  DEBT_INPUT(3, "DEBT_INPUT"),
  DEBT_END(Integer.MAX_VALUE, "DEBT_END");
  private final Integer id;
  private final String name;
}
