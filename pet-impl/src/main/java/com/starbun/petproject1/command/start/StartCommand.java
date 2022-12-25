package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.command.CommandNames;
import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.starbun.petproject1.command.start.StartCommandStates.START_OPTIONS;

@Slf4j
@Component(CommandNames.COMMAND_START)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class StartCommand extends BasicCommand {

  @Getter
  @Setter
  // Операционные данные (состояние команды)
  private StartCommandStates currentState = START_OPTIONS;

  // Бины/сервисы
  private final TelegramUserService telegramUserService;
  private final StartKeyboardService startKeyboardService;

  public StartCommand(TelegramUserService telegramUserService, StartKeyboardService startKeyboardService) {
    super(CommandNames.COMMAND_START, "Стартовая команда бота");
    this.telegramUserService = telegramUserService;
    this.startKeyboardService = startKeyboardService;
  }

  /**
   * Метод срабатывает только при наличии "/start" в начале текстового сообщения
   */
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    switch (chat.getType()) {
      case "private" -> {
        TelegramUserDto telegramUser = telegramUserService.registerOfFetchUser(user);
        SendMessage greetingsMessage = createGreetingsMessage(chat, telegramUser, userOwnerId);
        send(absSender, greetingsMessage);
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
   * Обработка обычных текстовых сообщений при работе с данной командой
   */
  @Override
  public void processMessage(AbsSender absSender, Message message, String[] arguments) {

  }

  /**
   * Создание начального приветственного сообщения
   */
  private SendMessage createGreetingsMessage(Chat chat, TelegramUserDto telegramUser, Long userId) {
    return SendMessage.builder()
        .chatId(chat.getId())
        .text("Стартуем, " + telegramUser.getActualUsername() + "!")
        .parseMode("Markdown")
        .replyMarkup(startKeyboardService.createForState(currentState, userId))
        .build();
  }
}
