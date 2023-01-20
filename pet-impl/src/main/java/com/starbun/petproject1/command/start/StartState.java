package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.CommandStates;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Все состояния работы с пользователем, в которых может пребывать данная команда
 */
@Getter
@AllArgsConstructor
public enum StartState implements CommandStates<StartActions> {
  START_BEGIN(0, "START_BEGIN", StartActions.RESTART, StartActions.SHOW_COMMANDS);
//  START_END(Integer.MAX_VALUE, "START_END", StartActions.RESTART, StartActions.SHOW_COMMANDS);
  private final Integer code;
  private final String name;
  private final List<StartActions> applicableActions;

  StartState(Integer code, String name, StartActions... actions) {
    this.code = code;
    this.name = name;
    this.applicableActions = Arrays.stream(actions).toList();
  }
}
