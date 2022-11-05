package com.starbun.petproject1.bot.processor;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработчик сообщений поступивших боту
 */
public interface MessageProcessor extends BasicProcessor<Message> {

  void processMessage(AbsSender absSender, Message message);
}
