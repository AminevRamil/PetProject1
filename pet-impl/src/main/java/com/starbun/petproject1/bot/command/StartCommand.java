package com.starbun.petproject1.bot.command;

import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.exception.TelegramApiMismatchException;
import com.starbun.petproject1.service.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommand extends BasicCommand {

  private final TelegramUserService telegramUserService;

  public StartCommand(TelegramUserService telegramUserService) {
    super("start", "Старт");
    this.telegramUserService = telegramUserService;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    switch (chat.getType()) {
      case "private" -> {
        TelegramUserDto telegramUser = telegramUserService.registerUser(user);
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chat.getId().toString());
        message.setText("Стартуем, " + telegramUser.getActualUsername() + "!");
        try {
          absSender.execute(message);
        } catch (TelegramApiException e) {
          //логируем сбой Telegram Bot API, используя commandName и userName
        }
      }
      case "group", "channel", "supergroup" -> {
        throw new NoImplementationException("Ещё нет обработки команды /start для чата типа " + chat.getType());
      }
      default -> {
        throw new TelegramApiMismatchException("Сообщение пришло из чата неизвестного типа: " + chat.getType());
      }
    }

  }
}
