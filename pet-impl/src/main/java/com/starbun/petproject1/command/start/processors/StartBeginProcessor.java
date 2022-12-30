package com.starbun.petproject1.command.start.processors;

import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.start.StartState;
import com.starbun.petproject1.command.start.StartActions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartBeginProcessor extends AbstractStateProcessor<StartState, StartActions> {
  @Getter
  private final StartState processingState = StartState.START_BEGIN;

  @Override
  public void process(StartActions action) {
    log.info("DebtBeginProcessor action: " + action.getName());
  }
}
