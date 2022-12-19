package com.starbun.petproject1.util;

import com.google.common.collect.ImmutableList;
import com.starbun.petproject1.dto.State;
import lombok.Data;

import java.util.List;

@Data
public class CommandStateMachine {
  private final List<State> states;
  private State currentState;

  public CommandStateMachine(State defaultState, State... otherStates) {
    this.states = ImmutableList.<State>builder().add(defaultState).add(otherStates).build();
    this.currentState = defaultState;
  }
}
