package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.KeyboardAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StartKeyboardActions implements KeyboardAction {
  SHOW_MENU(0),
  RESTART(1);

  @Getter
  private Integer code;
}
