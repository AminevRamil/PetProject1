package com.starbun.petproject1.processor;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработчик обновлений поступивших боту.
 * Распределяет обновления по конкретным обработчикам.
 */
public interface UpdateResolver {

  void process(AbsSender absSender, Update update);
}
