package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.AbstractStateProcessor;
import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.start.state.StartStateMachine;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    switch (chat.getType()) {
      case "private" -> {
//        TelegramUserDto telegramUser = telegramUserService.registerOfFetchUser(user);
//        SendMessage greetingsMessage = createGreetingsMessage(chat, telegramUser, userOwnerId);
//        lastMessageFromBot = send(absSender, greetingsMessage);
      }
      case "group", "channel", "supergroup" -> {
        throw new NoImplementationException("Ещё нет обработки команды /start для чата типа " + chat.getType());
      }
      default -> {
        throw new TelegramApiMismatchException("Сообщение пришло из чата неизвестного типа: " + chat.getType());
      }
    }
  }

  /**
   * Создание начального приветственного сообщения
   */
  private SendMessage createGreetingsMessage(Chat chat, TelegramUserDto telegramUser, Long userId) {
    return SendMessage.builder()
        .chatId(chat.getId())
        .text("Стартуем, " + telegramUser.getActualUsername() + "!")
        .parseMode("Markdown")
//        .replyMarkup(startKeyboardService.createForState(currentState, userId))
        .build();
  }
}
