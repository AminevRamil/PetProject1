package com.starbun.petproject1.command.debt.processor;

import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.debt.state.DebtState;
import com.starbun.petproject1.command.debt.state.DebtActions;
import com.starbun.petproject1.dto.ProcessorRequest;
import com.starbun.petproject1.dto.StateProcessorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebtBeginProcessor extends AbstractStateProcessor<DebtState, DebtActions> {
  @Getter
  private final DebtState processingState = DebtState.DEBT_BEGIN;

  @Override
  public StateProcessorResponse process(ProcessorRequest<DebtActions> request) {
    log.info("DebtBeginProcessor action: " + request.getAction().getName());
    return null;
  }
}
