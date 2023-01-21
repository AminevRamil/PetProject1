package com.starbun.petproject1.processor;

import com.starbun.petproject1.model.UpdateType;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface BotApiObjectProcessor<T extends BotApiObject> {

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
}
