package com.starbun.petproject1.command.debt.state;

import com.starbun.petproject1.command.AbstractStateMachine;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.dto.DebtDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DebtStateMachine extends AbstractStateMachine<DebtState, DebtActions> {

  private DebtDraft currentEditingDebt = null;

  @Autowired
  public DebtStateMachine(List<AbstractStateProcessor<DebtState, DebtActions>> processors) {
    currentState = DebtState.DEBT_BEGIN;
    stateProcessors = processors.stream()
        .collect(Collectors.toMap(AbstractStateProcessor::getProcessingState, Function.identity()));
    // TODO добавить проверку, что есть обработчик для каждого состояния
  }
}
