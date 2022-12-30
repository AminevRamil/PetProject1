package com.starbun.petproject1.command.start.state;

import com.starbun.petproject1.command.AbstractStateMachine;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.start.StartActions;
import com.starbun.petproject1.command.start.StartState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StartStateMachine extends AbstractStateMachine<StartState, StartActions> {

  @Autowired
  public StartStateMachine(List<AbstractStateProcessor<StartState, StartActions>> processors) {
    currentState = StartState.START_BEGIN;
    stateProcessors = processors.stream()
        .collect(Collectors.toMap(AbstractStateProcessor::getProcessingState, Function.identity()));
    // TODO добавить проверку, что есть обработчик для каждого состояния
  }
}
