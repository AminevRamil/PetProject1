package com.starbun.petproject1.bot.processor;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Обработчик сообщений поступивших боту
 */
public interface MessageProcessor extends BasicProcessor<Message> {

  void processMessage(Message message);
}
