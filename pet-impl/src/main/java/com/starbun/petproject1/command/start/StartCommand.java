package com.starbun.petproject1.command.start;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.command.CommandStates;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.TelegramUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.starbun.petproject1.command.start.StartCommand.StartCommandStates.START_OPTIONS;

@Slf4j
@Component
@Scope("prototype")
public class StartCommand extends BasicCommand {

  /**
   * Все состояния работы с пользователем, в которых может пребывать данная команда
   */
  @Getter
  @AllArgsConstructor
  public enum StartCommandStates implements CommandStates {
    START_OPTIONS(0, "START_OPTIONS"),
    START_END(Integer.MAX_VALUE, "START_END");
    private final Integer id;
    private final String name;
  }

  // Операционные данные (состояние команды)
  @Getter
  private StartCommand.StartCommandStates currentState = START_OPTIONS;
  @Setter
  @Getter
  private Long userOwnerId;

  // Бины/сервисы
  private final TelegramUserService telegramUserService;
  private final StartKeyboardService startKeyboardService;

  public StartCommand(TelegramUserService telegramUserService, StartKeyboardService startKeyboardService) {
    super("start", "Стартовая команда бота");
    this.telegramUserService = telegramUserService;
    this.startKeyboardService = startKeyboardService;
  }

  /**
   * Метод срабатывает только при наличии "/start" в начале текстового сообщения
   */
  @Override
  public void processMessage(AbsSender absSender, Message message, String[] arguments) {
    switch (message.getChat().getType()) {
      case "private" -> {
        TelegramUserDto telegramUser = telegramUserService.registerOfFetchUser(message.getFrom());
        SendMessage greetingsMessage = createGreetingsMessage(message.getChat(), telegramUser);
        send(absSender, greetingsMessage);
      }
      case "group", "channel", "supergroup" -> {
        throw new NoImplementationException("Ещё нет обработки команды /start для чата типа " + message.getChat().getType());
      }
      default -> {
        throw new TelegramApiMismatchException("Сообщение пришло из чата неизвестного типа: " + message.getChat().getType());
      }
    }
  }

  /**
   * Создание начального приветственного сообщения
   */
  private SendMessage createGreetingsMessage(Chat chat, TelegramUserDto telegramUser) {
    return SendMessage.builder()
        .chatId(chat.getId())
        .text("Стартуем, " + telegramUser.getActualUsername() + "!")
        .parseMode("Markdown")
        .replyMarkup(startKeyboardService.createForState(currentState, userOwnerId))
        .build();
  }
}
