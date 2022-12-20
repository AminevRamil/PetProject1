package com.starbun.petproject1.processor;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработчик сообщений поступивших боту
 */
public interface MessageProcessor extends BasicProcessor<Message> {

  void process(AbsSender absSender, Message message);
}
