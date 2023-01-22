package com.starbun.petproject1.command;

/**
 * Общий интерфейс для перечисления всех возможных действий бота.
 * Коды/названия действий которые будут сохраняться как информация для кнопок.
 */
public interface CommandActions {
  /**
   * Код команды
   */
  Integer getCode();

  /**
   * Название команды
   */
  String getName();
}
