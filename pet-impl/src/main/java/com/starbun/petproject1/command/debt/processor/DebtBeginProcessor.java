package com.starbun.petproject1.command.debt.processor;

import com.starbun.petproject1.command.StateProcessor;
import com.starbun.petproject1.command.debt.DebtCommandStates;
import com.starbun.petproject1.command.debt.DebtKeyboardActions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebtBeginProcessor implements StateProcessor<DebtKeyboardActions> {
  @Getter
  private final DebtCommandStates processingState = DebtCommandStates.DEBT_BEGIN;

  @Override
  public void process(DebtKeyboardActions action) {
    log.info("asdasd");
  }
}
