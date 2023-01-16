package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.processor.MessageProcessor;
import com.starbun.petproject1.service.TelegramUserService;
import com.starbun.petproject1.util.CommandsLifeCycleManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand.COMMAND_INIT_CHARACTER;
import static org.telegram.telegrambots.meta.api.objects.EntityType.BOTCOMMAND;

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
    TelegramUserDto telegramUser = telegramUserService.registerOrFetchUser(message.getFrom());
    if (message.isCommand()) {
      String commandName = getCommand(message);
      AbstractCommand commandByUserId = commandsLifeCycleManager.getCommandByUserId(telegramUser.getId(), commandName);
      // TODO 1 Если пришедший код команды не совпадает с тем, что в хранилище, то пересоздать команду.
      // TODO 2 как вариант сделать метод для закрытия команды и она проверяет, можно ли себя закрыть на данном этапе или нет
      // TODO 2.1 Проверять можно по текущему состоянию (MAX_INT)
      commandByUserId.execute(absSender, message.getFrom(), message.getChat(), message.getMessageId(), null);
    } else {
      processNonCommandMessage(absSender, message);
    }
  }

  private void processNonCommandMessage(AbsSender absSender, Message message) {
    TelegramUserDto telegramUser = telegramUserService.registerOrFetchUser(message.getFrom());
    AbstractCommand commandByUserId = commandsLifeCycleManager.getCommandByUserId(telegramUser.getId(), null);
    commandByUserId.processMessage(absSender, message, null);
  }

  /**
   * Извлекает команду в начале текстового сообщения.
   *
   * @param message сообщение содержащее команду
   * @return текст команды без символ "/" (COMMAND_INIT_CHARACTER)
   */

  private String getCommand(Message message) {
    return message.getEntities().stream()
        .filter(me -> me.getOffset() == 0)
        .filter(me -> BOTCOMMAND.equals(me.getType()))
        .map(MessageEntity::getText)
        .map(cmdStr -> cmdStr.startsWith(COMMAND_INIT_CHARACTER) ? cmdStr.substring(1) : cmdStr)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("В данном сообщении нет команды в начале: messageId " + message.getMessageId()));
  }

}
