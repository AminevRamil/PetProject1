package com.starbun.petproject1.command.debt.processor;


import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.debt.state.DebtState;
import com.starbun.petproject1.command.debt.state.DebtActions;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DebtChoseProcessor extends AbstractStateProcessor<DebtState, DebtActions> {
  @Getter
  private final DebtState processingState = DebtState.DEBT_CHOSE;

  @Override
  public void process(DebtActions action) {
    log.info("DebtChoseProcessor action: " + action.getName());
  }
}
