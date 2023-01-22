package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.CommandResponse;
import com.starbun.petproject1.exception.NoImplementationException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;

import static com.starbun.petproject1.util.CommandsLifeCycleManager.TIME_TO_LIVE;

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

  public void setLastMessageFromBot() {
    throw new NoImplementationException("setLastMessageFromBot");
    //TODO Написать логику заполнения двух верхних полей через один сеттер
  }

  public void getLastMessageFromBot() {
    throw new NoImplementationException("getLastMessageFromBot");
  }

  /**
   * Машина состояний для обработки действий пользователя
   *
   * @see AbstractStateMachine
   */
  @Getter
  protected AbstractStateMachine<? extends CommandStates<? extends CommandActions>, ? extends CommandActions> stateMachine;

  /**
   * Обработка команды/сообщения начинающегося с "/getCommandIdentifier()"
   *
   * @see this.executeWithResponse(...)
   */
  @Override
  @Deprecated
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    throw new IllegalStateException("Для команды /" + getCommandIdentifier() + " не описана реакция");
  }

  /**
   * Обработка команды/сообщения начинающегося с "/getCommandIdentifier()"
   *
   * @param absSender бот отправляющий сообщения
   * @param user      пользователь, отправивший команду
   * @param chat      чат для отправки ответа
   * @param messageId идентификатор сообщения для взаимодействия
   * @param arguments передаваемые аргументы
   * @return Результат работы команды с описанием необходимых действий
   */
  public CommandResponse executeWithResponse(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    throw new IllegalStateException("Для команды /" + getCommandIdentifier() + " не описана реакция");
  }

  /**
   * Метод обработки инлайн-кнопки команды
   */
  public CommandResponse executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery) {
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
   * Обработка текстовых запросов.
   * Не рекомендуется использовать т.к. не имеет возвращаемого во вне результата
   *
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
    return CommandResponse.builder()
        .replyToMessageId(lastMessageFromBot != null ? lastMessageFromBot.getMessageId() : null)
        .chatId(message.getChatId())
        .messageText("Данная команда не поддерживает работу с текстом. Используйте кнопки")
        .build();
  }
}
