package com.starbun.petproject1.command.start.processors;

import com.starbun.petproject1.command.StateProcessor;
import com.starbun.petproject1.command.start.StartCommandStates;
import com.starbun.petproject1.command.start.StartKeyboardActions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartBeginProcessor implements StateProcessor<StartKeyboardActions> {
  @Getter
  private final StartCommandStates processingState = StartCommandStates.START_OPTIONS;

  @Override
  public void process(StartKeyboardActions action) {

  }
}
