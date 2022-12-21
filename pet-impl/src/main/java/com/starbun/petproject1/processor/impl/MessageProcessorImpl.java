package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.processor.MessageProcessor;
import com.starbun.petproject1.service.TelegramUserService;
import com.starbun.petproject1.util.CommandsLifeCycleManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обрабатывает сообщения от пользователей.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {

  private final TelegramUserService telegramUserService;

  private final CommandsLifeCycleManager commandsLifeCycleManager;

  @Override
  public void process(AbsSender absSender, Message message) {
    log.info("Пришло сообщение от пользователя {}", message.getFrom().getId());

    TelegramUserDto telegramUser = telegramUserService.registerOfFetchUser(message.getFrom());
    BasicCommand commandByUserId = commandsLifeCycleManager.getCommandByUserId(telegramUser.getId());
    commandByUserId.processMessage(absSender, message, null);
  }
}
