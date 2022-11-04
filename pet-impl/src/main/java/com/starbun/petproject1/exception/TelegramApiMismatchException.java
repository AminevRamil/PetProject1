package com.starbun.petproject1.exception;

/**
 * Исключение выбрасываемое в ситуациях, когда пришедшая от сервера телеграм не соответствуют
 * описанию в библиотеке. Например, если приходит название типа, которое не должно быть по документации,
 * или, если приходят несколько объектов разных сообщения в одном обновлении.
 */
public class TelegramApiMismatchException extends RuntimeException {
  public TelegramApiMismatchException(String message){
    super(message);
  }
}
