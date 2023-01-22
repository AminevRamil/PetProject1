package com.starbun.petproject1.service.impl;

import com.starbun.petproject1.dto.CommandResponse;
import com.starbun.petproject1.service.CommandResponseResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Service
public class CommandResponseResolverImpl implements CommandResponseResolver {

  // TODO Исправить циклическую зависимость
  @Lazy
  @Autowired
  private AbsSender absSender;

  @Override
  public Boolean sendToTelegram(CommandResponse commandResponse) {
    send(absSender, generateMethod(commandResponse));
    return null;
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> Method generateMethod(CommandResponse commandResponse) {
    // TODO Удалить свитч
    switch (commandResponse.getCommandResponseType()) {
      case NEW_MESSAGE -> {
        // TODO Проверить каст
        return (Method) SendMessage.builder()
            .text(commandResponse.getMessageText())
            .replyMarkup(commandResponse.getReplyMarkup())
            .chatId(commandResponse.getChatId())
            .build();
      }
      case ALTER_MESSAGE -> {
      }
    }
    return null;
  }


  private <T extends Serializable, Method extends BotApiMethod<T>> T send(AbsSender absSender, Method method) {
    try {
      return absSender.execute(method);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
