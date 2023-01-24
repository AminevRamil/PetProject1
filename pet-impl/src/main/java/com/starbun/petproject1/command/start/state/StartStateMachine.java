package com.starbun.petproject1.command.start.state;

import com.starbun.petproject1.command.AbstractStateMachine;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.start.StartActions;
import com.starbun.petproject1.command.start.StartState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StartStateMachine extends AbstractStateMachine<StartState, StartActions> {

  @Autowired
  public StartStateMachine(List<AbstractStateProcessor<StartState, StartActions>> processors) {
    currentState = StartState.START_BEGIN;
    stateProcessors = processors.stream()
        .collect(Collectors.toMap(AbstractStateProcessor::getProcessingState, Function.identity()));

    if (!Arrays.stream(StartState.values()).allMatch(stateProcessors::containsKey)) {
      throw new RuntimeException();
    }
  }
}
