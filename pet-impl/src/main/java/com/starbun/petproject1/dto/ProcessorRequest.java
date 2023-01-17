package com.starbun.petproject1.dto;

import com.starbun.petproject1.command.CommandActions;
import lombok.Data;

@Data
public class ProcessorRequest<A extends CommandActions> {
  private Long userId;
  private A action;
}
