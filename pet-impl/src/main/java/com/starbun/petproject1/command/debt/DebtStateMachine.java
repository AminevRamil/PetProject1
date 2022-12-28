package com.starbun.petproject1.command.debt;

import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.command.StateProcessor;
import com.starbun.petproject1.dto.DebtDraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DebtStateMachine {

  private DebtDraft currentEditingDebt = null;
  private DebtCommandStates currentState = DebtCommandStates.DEBT_BEGIN;
  private final Map<CommandStates, StateProcessor<DebtKeyboardActions>> stateProcessors;

  @Autowired
  public DebtStateMachine(List<StateProcessor<DebtKeyboardActions>> processors) {
    stateProcessors = processors.stream()
        .collect(Collectors.toMap(StateProcessor::getProcessingState, Function.identity()));
    // TODO добавить проверку, что есть обработчик для каждого состояния
  }

  public void performAction(DebtKeyboardActions action) {
    if (!stateProcessors.containsKey(currentState)) {
      throw new IllegalArgumentException("Обработчик не может обработать текущее состояние: " + currentState);
    }
    if (!currentState.getApplicableActions().contains(action)) {
      throw new UnsupportedOperationException(String.format(
          "Заданное действие %s не поддерживается в текущем состоянии %s", action.getCode(), currentState.getName()));
    }
    stateProcessors.get(currentState).process(action);

  }
}
