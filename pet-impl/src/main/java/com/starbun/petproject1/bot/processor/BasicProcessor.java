package com.starbun.petproject1.bot.processor;

import com.starbun.petproject1.exception.NoImplementationException;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public interface BasicProcessor<T extends BotApiObject> {
  default void process(T t) {
    throw new NoImplementationException("Обработчик данного обновления не реализован");
  }

  /**
   * Простая обёртка над процессом выполнения отправки ответов телеграм-ботом, чтобы
   * не приходилось постоянно засовывать отправку в try-catch блоки с типичной логикой обработки.
   */
  default <S extends Serializable, Method extends BotApiMethod<S>> S send(AbsSender absSender, Method method)  {
    try {
      return absSender.execute(method);
    } catch (TelegramApiException e) {
      throw new RuntimeException("В ходе отправки сообщения произошла ошибка", e);
    }
  }
}
