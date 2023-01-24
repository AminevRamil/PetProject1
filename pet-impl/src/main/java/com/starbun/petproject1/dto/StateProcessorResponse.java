package com.starbun.petproject1.dto;

import com.starbun.petproject1.command.CommandActions;
import com.starbun.petproject1.command.CommandStates;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class StateProcessorResponse {
  private CommandResponseType responseType;
  private CommandStates<? extends CommandActions> newState;
  private String messageText;
}
