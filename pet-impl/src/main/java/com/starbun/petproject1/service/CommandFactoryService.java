package com.starbun.petproject1.service;

import com.starbun.petproject1.command.AbstractCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания бина команды по его названию.
 * TODO Подумать над менее костыльным решением, без использования контекста.
 * TODO Подумать в сторону Map<String, ConcreteCommandFactory>
 */
@Component
@RequiredArgsConstructor
public class CommandFactoryService  {

  private final ApplicationContext appCtx;

  public AbstractCommand createCommand(String commandName) {
    return (AbstractCommand) appCtx.getBean(commandName);
  }
}
