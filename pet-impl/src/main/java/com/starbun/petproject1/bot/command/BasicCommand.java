package com.starbun.petproject1.bot.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;

@Slf4j
public abstract class BasicCommand extends DefaultBotCommand {

  public BasicCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }

}
