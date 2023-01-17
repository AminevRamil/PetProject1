package com.starbun.petproject1.dto;

import com.starbun.petproject1.command.CommandActions;
import com.starbun.petproject1.command.CommandStates;
import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class ProcessorResponse {
  private CommandStates<? extends CommandActions> newState;
  private BotApiMethod<? extends Serializable> method;
}
