package com.starbun.petproject1.command.debt.processor;


import com.starbun.petproject1.command.StateProcessor;
import com.starbun.petproject1.command.debt.DebtCommandStates;
import com.starbun.petproject1.command.debt.DebtKeyboardActions;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class DebtInputProcessor implements StateProcessor<DebtKeyboardActions> {
  @Getter
  private final DebtCommandStates processingState = DebtCommandStates.DEBT_INPUT;

  @Override
  public void process(DebtKeyboardActions action) {

  }
}
