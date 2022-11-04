package com.starbun.petproject1.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;

/**
 * Маркер, по которому Spring автоматически собирает все команды, для последующей регистрации в боте.
 *
 * В будущем можно сделать какой-то метод для обобщённой обработки ответов, включающий try-catch конструкцию,
 * чтоб её не надо было писать в самой команде
 */
@Slf4j
public abstract class BasicCommand extends DefaultBotCommand {

  public BasicCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }

}
