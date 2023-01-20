package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.CommandResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
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
 * Абстрактный класс для всех команд. Содержит в себе элементы общей логики и необходимые для работы команды поля.
 */
@Slf4j
public abstract class AbstractCommand extends DefaultBotCommand {

  /**
   * Срок годности команды, после которого сессия завершится
   */
  @Getter
  @Setter
  protected LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TIME_TO_LIVE);

  /**
   * Объект(ы) с информацией о последнем сообщении бота
   */
  protected Message lastMessageFromBot;
  protected BotApiObject lastBotApiObject;

  /**
   * Машина состояний для обработки действий пользователя
   * @see AbstractStateMachine
   */
  @Getter
  protected AbstractStateMachine<? extends CommandStates<? extends CommandActions>, ? extends CommandActions> stateMachine;

  public void executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery){
    throw new IllegalStateException("Для команды /" + getCommandIdentifier() + " не описана реакция на инлайн-кнопки");
  }

  /**
   * Необходимость заполнить эти два поля, делает невозможным создания простого конструктора
   * для инъекции зависимостей через ломбок, используя AllArgsConstructor. Поэтому следует создать
   * свой конструктор с получением и заданием необходимых зависимостей, но предварительно вызвав
   * Данный родительский конструктор.
   */
  public AbstractCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }


  /**
   * Не рекомендуется использовать т.к. не имеет возвращаемого во вне результата
   * @see this.processMessageWithResponse(...)
   */
  @Override
  @Deprecated
  public void processMessage(AbsSender absSender, Message message, String[] arguments) {
    super.processMessage(absSender, message, arguments);
  }

  /**
   * Обработка обычных текстовых сообщений при работе с данной командой
   *
   * @return результат работы команды в виде описания того, что нужно сделать
   */
  public CommandResponse processMessageWithResponse(AbsSender absSender, Message message, String[] arguments) {
    SendMessage sendMessage = SendMessage.builder()
        .replyToMessageId(lastMessageFromBot != null ? lastMessageFromBot.getMessageId() : null)
        .chatId(lastMessageFromBot != null? lastMessageFromBot.getChatId(): message.getChatId())
        .parseMode(MARKDOWN)
        .text("Данная команда не поддерживает работу с текстом. Используйте кнопки")
        .build();
    lastMessageFromBot = send(absSender, sendMessage);
    return new CommandResponse();
  }

  /**
   * TODO Вынести этот функционал в CommandResponseResolver
   * Простая обёртка над процессом выполнения отправки ответов телеграм-ботом, чтобы
   * не приходилось постоянно засовывать отправку в try-catch блоки с типичной логикой обработки.
   *
   * @param absSender бот, исполняющий запросы к телеграму
   * @param method действие, которое необходимо совершить боту
   * @return результат в виде описания сообщения, которое создал бот.
   * @param <B> Объект показывающий результат отправки запроса к телеграму
   *            TODO может вернуть и другие объекты, проверить исходники
   * @param <T> сериализуемый объект
   * @param <Method> запрос к телеграму
   */
  @Deprecated
  protected <B extends BotApiObject, T extends Serializable, Method extends BotApiMethod<T>> B send(AbsSender absSender, Method method)  {
    try {
      // TODO Проверить каст
      return (B) absSender.execute(method);
    } catch (TelegramApiException e) {
      log.error("В ходе отправки сообщения произошла ошибка: ", e);
      throw new RuntimeException("В ходе отправки сообщения произошла ошибка", e);
    }
  }
}
