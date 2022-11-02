package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.processor.MessageProcessor;
import com.starbun.petproject1.entity.TelegramUser;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessorImpl implements MessageProcessor {

  private final TelegramUserService telegramUserService;

  @Override
  public void processMessage(Message message) {
    log.info("Пришло сообщение от пользователя {}", message.getFrom().getId());
    TelegramUser telegramUser = telegramUserService.registerUser(message.getFrom());
  }
}
