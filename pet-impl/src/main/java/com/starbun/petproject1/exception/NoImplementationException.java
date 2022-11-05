package com.starbun.petproject1.exception;

/**
 * Исключение показывающее, что не существует реализации обработки сообщений/типов объектов,
 * существующих согласно документации
 */
public class NoImplementationException extends RuntimeException {
  public NoImplementationException(String message) {
    super(message);
  }
}
