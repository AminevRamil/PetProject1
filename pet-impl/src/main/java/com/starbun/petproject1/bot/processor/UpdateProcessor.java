package com.starbun.petproject1.bot.processor;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Обработчик обновлений поступивших боту
 */
public interface UpdateProcessor {

  void process(Update update);
}
