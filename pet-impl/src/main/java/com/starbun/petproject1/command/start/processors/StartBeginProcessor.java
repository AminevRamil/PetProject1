package com.starbun.petproject1.command.start.processors;

import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.start.StartKeyboardService;
import com.starbun.petproject1.command.start.StartState;
import com.starbun.petproject1.command.start.StartActions;
import com.starbun.petproject1.dto.ProcessorRequest;
import com.starbun.petproject1.dto.ProcessorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartBeginProcessor extends AbstractStateProcessor<StartState, StartActions> {
  @Getter
  private final StartState processingState = StartState.START_BEGIN;

  @Override
  public ProcessorResponse process(ProcessorRequest<StartActions> request) {
    log.debug("StartBeginProcessor action: " + request.getAction().getName());
    switch (request.getAction()) {
      case SHOW_COMMANDS -> {
        return ProcessorResponse.builder()
            .method(createMenuMessage(request))
            .build();
      }
      case RESTART -> {

      }
      default -> {
        log.error("В текущем состоянии ({}) невозможно выполнить действие {}", processingState, request.getAction());
      }
    }
    return null;
  }

  private SendMessage createMenuMessage(ProcessorRequest<StartActions> request) {
    return SendMessage.builder()
        .text("Список команд:\n/start - старт\n/debt - долги\n")
        .build();
  }
}
