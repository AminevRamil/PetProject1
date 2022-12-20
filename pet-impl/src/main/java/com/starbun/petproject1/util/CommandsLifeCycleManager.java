package com.starbun.petproject1.util;

import com.starbun.petproject1.command.BasicCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandsLifeCycleManager {

  private final Map<Long, BasicCommand> userToCommandMap = new HashMap<>();

  public BasicCommand getCommandByUserId(Long userId) {
    /**
     * TODO
     * Вернуть либо новую команду (но тогда надо передать требуемый тип команды для создания на лету),
     * либо вернуть нулл и во вне обработать через создание команды
     */
    return userToCommandMap.getOrDefault(userId, null);
  }

  public void storeNewCommand(BasicCommand command) {

  }
}
