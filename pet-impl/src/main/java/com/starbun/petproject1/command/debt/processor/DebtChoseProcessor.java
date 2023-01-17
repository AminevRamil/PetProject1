package com.starbun.petproject1.command.debt.processor;


import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.debt.state.DebtState;
import com.starbun.petproject1.command.debt.state.DebtActions;
import com.starbun.petproject1.dto.ProcessorRequest;
import com.starbun.petproject1.dto.ProcessorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebtChoseProcessor extends AbstractStateProcessor<DebtState, DebtActions> {
  @Getter
  private final DebtState processingState = DebtState.DEBT_CHOSE;

  @Override
  public ProcessorResponse process(ProcessorRequest<DebtActions> request) {
    log.info("DebtChoseProcessor action: " + request.getAction().getName());
    return null;
  }
}
