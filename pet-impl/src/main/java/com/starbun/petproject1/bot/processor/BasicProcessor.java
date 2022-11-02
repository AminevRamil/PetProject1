package com.starbun.petproject1.bot.processor;

import com.starbun.petproject1.exception.NoImplementationException;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

public interface BasicProcessor<T extends BotApiObject> {
  default void process(T t) {
    throw new NoImplementationException("Обработчик данного обновления не реализован");
  }
}
