package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.start.state.StartStateMachine;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.dto.ProcessorResponse;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Slf4j
@Component(CommandNames.COMMAND_START)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StartCommand extends AbstractCommand {

  @Getter
  private final StartStateMachine stateMachine;

  // Бины/сервисы
  private final TelegramUserService telegramUserService;
  private final StartKeyboardService startKeyboardService;

  public StartCommand(TelegramUserService telegramUserService, StartKeyboardService startKeyboardService, List<AbstractStateProcessor<StartState, StartActions>> processors) {
    super(CommandNames.COMMAND_START, "Стартовая команда бота");
    this.telegramUserService = telegramUserService;
    this.startKeyboardService = startKeyboardService;
    this.stateMachine = new StartStateMachine(processors);
  }

  /**
   * Метод срабатывает только при наличии "/start" в начале текстового сообщения
   */
  // TODO Добавить дефолтную реализацию в абстрактный класс
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    switch (chat.getType()) {
      case "private" -> {
        send(absSender, createGreetingsMessage(chat));
      }
      case "group", "channel", "supergroup" -> {
        throw new NoImplementationException("Ещё нет обработки команды /start для чата типа " + chat.getType());
      }
      default -> {
        throw new TelegramApiMismatchException("Сообщение пришло из чата неизвестного типа: " + chat.getType());
      }
    }
  }

  @Override
  public void executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery) {

    var info = new InlineButtonInfo(callbackQuery.getData());
    var action = StartActions.fromCode(info.getKeyboardActionCode());

    try {
      ProcessorResponse processorResponse = stateMachine.performAction(action);
      lastBotApiObject =  send(absSender, processorResponse.getMethod());
    } catch (Exception e) {
      log.error("Ошибка обработки действия инлайн кнопки: ", e);
    }
  }

  /**
   * Создание начального приветственного сообщения
   */
  private SendMessage createGreetingsMessage(Chat chat) {
    return SendMessage.builder()
        .chatId(chat.getId())
        .text("{Приветственный текст}")
        .replyMarkup(startKeyboardService.createForState(stateMachine.getCurrentState(), stateMachine.getUserOwnerId()))
        .build();
  }
}
