package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.InlineButtonInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.starbun.petproject1.util.CommandsLifeCycleManager.TIME_TO_LIVE;

/**
 * Маркер, по которому Spring автоматически собирает все команды, для последующей регистрации в боте.
 *
 * В будущем можно сделать какой-то метод для обобщённой обработки ответов, включающий try-catch конструкцию,
 * чтоб её не надо было писать в самой команде
 */
@Slf4j
public abstract class BasicCommand extends DefaultBotCommand {

  @Getter
  @Setter
  private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(TIME_TO_LIVE);

  protected abstract CommandStates getCurrentState();

  abstract public void executeInlineButton(AbsSender absSender, CallbackQuery message, InlineButtonInfo buttonData);

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
