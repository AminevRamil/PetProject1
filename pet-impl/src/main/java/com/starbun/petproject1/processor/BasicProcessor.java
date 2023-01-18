package com.starbun.petproject1.processor;

import com.starbun.petproject1.model.UpdateType;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public interface BasicProcessor<T extends BotApiObject> {

  /**
   * Тип обрабатываемого объекта
   * @implNote В текущей реализации, кажется, что на каждый тип объекта,
   * возможно существование только одного обработчика из применения мапы и автовайра (пересечение ключей)
   */
  UpdateType getProcessingType();

  /**
   * Обработка объекта, соответствующего обработчику
   * @implNote Проверяется наличие обработчика на этапе компиляции
   */
  void process(AbsSender absSender, T t);

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
