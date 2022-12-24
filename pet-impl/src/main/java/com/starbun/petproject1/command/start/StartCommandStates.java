package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.CommandStates;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Все состояния работы с пользователем, в которых может пребывать данная команда
 */
@Getter
@AllArgsConstructor
public enum StartCommandStates implements CommandStates {
  START_OPTIONS(0, "START_OPTIONS"),
  START_END(Integer.MAX_VALUE, "START_END");
  private final Integer id;
  private final String name;
  // private boolean camBeEasilyEnded; ???
}
