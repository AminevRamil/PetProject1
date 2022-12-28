package com.starbun.petproject1.command;

public interface StateProcessor<T extends KeyboardAction> {

  CommandStates getProcessingState();

  void process(T action);
}
