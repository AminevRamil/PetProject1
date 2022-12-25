package com.starbun.petproject1.command.debt;


import com.starbun.petproject1.command.KeyboardAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DebtKeyboardActions implements KeyboardAction {
  DEBT_OPTIONS(0),
  DEBT_CREATE(1),
  DEBT_END(2),
  DEBT_SHOW_ALL(3),
  DEBT_SET_DEBTOR(4),
  DEBT_SET_DATE(5),
  DEBT_SET_SUBJECT(6),
  DEBT_SET_STATUS(6),
  DEBT_CANCEL(7),
  DEBT_SAVE(8),
  DEBT_DELETE(9),
  DEBT_CHANGE(10),
  DEBT_NEXT(11);

  @Getter
  private final int code;

  public String prepareButtonInfo() {
    return Integer.toString(code);
  }
}
