package com.starbun.petproject1;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.processor.UpdateResolver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component("StarBunBot")
@RequiredArgsConstructor
public class StarBunBot extends TelegramLongPollingBot {

  @Getter
  @Value("${telegram.bot.token}")
  private String botToken;

  @Getter
  @Value("${telegram.bot.name}")
  private String botUsername;

  private final List<AbstractCommand> commandList;

  private final UpdateResolver updateResolver;

  @PostConstruct
  public void init() {
    initCommandMenu();
  }

  @Override
  public void onUpdateReceived(Update update) {
    updateResolver.process(this, update);
  }

  @Override
  public void onRegister() {
    super.onRegister();
    log.info("Бот успешно зарегистрирован в Telegram API");
    // TODO Можно кинуть уведомление о старте бота
  }

  /**
   * Заполнение меню команд для удобного просмотра пользователем.
   *
   * @implNote Добавить разный список команд для разных видов чатов,
   * или ограничить использование бота в некоторых типах чата.
   */
  private void initCommandMenu() {
    // TODO Сделать иной способ заполнения меню с командами.
    List<BotCommand> botCommandList = commandList.stream()
        .map(basicCommand -> new BotCommand(basicCommand.getCommandIdentifier(), basicCommand.getDescription()))
        .toList();
    try {
      this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

}
