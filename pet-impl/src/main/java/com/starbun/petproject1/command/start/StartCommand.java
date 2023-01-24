package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.start.state.StartStateMachine;
import com.starbun.petproject1.dto.ActionResponse;
import com.starbun.petproject1.dto.CommandResponse;
import com.starbun.petproject1.dto.CommandResponseType;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.InlineKeyboardService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Slf4j
@Component(CommandNames.COMMAND_START)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StartCommand extends AbstractCommand {

  @Getter
  private final StartStateMachine stateMachine;

  private final InlineKeyboardService<StartState, StartActions> startKeyboardService;

  public StartCommand(InlineKeyboardService<StartState, StartActions> startKeyboardService,
                      List<AbstractStateProcessor<StartState, StartActions>> processors) {
    super(CommandNames.COMMAND_START, "Стартовая команда бота");
    this.startKeyboardService = startKeyboardService;
    this.stateMachine = new StartStateMachine(processors);
  }

  /**
   * Обработка команды/сообщения начинающегося с /start
   */
  @Override
  public CommandResponse executeWithResponse(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    switch (chat.getType()) {
      case "private" -> {
        return createGreetingsMessage(chat);
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
  public CommandResponse executeInlineButton(AbsSender absSender, CallbackQuery callbackQuery) {

    var info = new InlineButtonInfo(callbackQuery.getData());
    var action = StartActions.fromCode(info.getKeyboardActionCode());

    try {
      ActionResponse actionResponse = stateMachine.performAction(action, stateMachine);
      return CommandResponse.builder()
          .commandResponseType(actionResponse.getResponseType())
          .messageText(actionResponse.getMessageText())
          .chatId(callbackQuery.getMessage().getChatId()) // (!)
          .build();
    } catch (Exception e) {
      log.error("Ошибка обработки действия инлайн кнопки: ", e);
    }
    return null;
  }

  /**
   * Создание начального приветственного сообщения
   */
  private CommandResponse createGreetingsMessage(Chat chat) {
    return CommandResponse.builder()
        .commandResponseType(CommandResponseType.NEW_MESSAGE)
        .messageText("{Приветственный текст}")
        .chatId(chat.getId())
        .replyMarkup(startKeyboardService.createForState(stateMachine.getCurrentState(), stateMachine.getUserOwnerId()))
        .build();
  }
}
