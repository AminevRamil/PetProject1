package com.starbun.petproject1.bot.processor;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработчик колбеков от инлайн кнопок поступивших боту
 */
public interface CallbackQueryProcessor extends BasicProcessor<CallbackQuery> {

  void process(AbsSender absSender, CallbackQuery message);
}
