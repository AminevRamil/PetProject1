package com.starbun.petproject1.command;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.starbun.petproject1.util.CommandsLifeCycleManager.TIME_TO_LIVE;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

/**
 * Базовый класс для всех команд. Содержит в себе элементы общей логики и необходимые для работы команды поля.
 */
@Slf4j
public abstract class BasicCommand extends DefaultBotCommand {

  @Getter
  @Setter
  protected LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TIME_TO_LIVE);

  @Setter
  @Getter
  protected Long userOwnerId;

  @Getter
  protected CommandStates currentState;

  protected Message lastMessageFromBot;

  public void executeInlineButton(AbsSender absSender, CallbackQuery message){
    throw new IllegalStateException("Для команды /" + getCommandIdentifier() + " не описана реакция на инлайн-кнопки");
  }

  /**
   * Необходимость заполнить эти два поля, делает невозможным создания простого конструктора
   * для инъекции зависимостей через ломбок, используя AllArgsConstructor. Поэтому следует создать
   * свой конструктор с получением и заданием необходимых зависимостей, но предварительно вызвав
   * Данный родительский конструктор.
   */
  public BasicCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }

  /**
   * Обработка обычных текстовых сообщений при работе с данной командой
   */
  @Override
  public void processMessage(AbsSender absSender, Message message, String[] arguments) {
    if (lastMessageFromBot == null) {
      return;
    }
    SendMessage sendMessage = SendMessage.builder()
        .replyToMessageId(lastMessageFromBot.getMessageId())
        .chatId(lastMessageFromBot.getChatId())
        .parseMode(MARKDOWN)
        .text("Данная команда не поддерживает работу с текстом. Используйте кнопки")
        .build();
    send(absSender, sendMessage);
  }

  /**
   * Простая обёртка над процессом выполнения отправки ответов телеграм-ботом, чтобы
   * не приходилось постоянно засовывать отправку в try-catch блоки с типичной логикой обработки.
   */
  protected <T extends Serializable, Method extends BotApiMethod<T>> T send(AbsSender absSender, Method method)  {
    try {
      return absSender.execute(method);
    } catch (TelegramApiException e) {
      log.error("В ходе отправки сообщения произошла ошибка: ", e);
      throw new RuntimeException("В ходе отправки сообщения произошла ошибка", e);
    }
  }
}
