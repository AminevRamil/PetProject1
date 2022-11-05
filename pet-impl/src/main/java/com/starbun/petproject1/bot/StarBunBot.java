package com.starbun.petproject1.bot;

import com.starbun.petproject1.bot.command.BasicCommand;
import com.starbun.petproject1.bot.processor.UpdateProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("StarBunBot")
@RequiredArgsConstructor
public class StarBunBot extends TelegramLongPollingCommandBot {

  @Getter
  @Value("${telegram.bot.token}")
  private String botToken;

  @Getter
  @Value("${telegram.bot.name}")
  private String botUsername;

  private final List<BasicCommand> commandList;

  private final UpdateProcessor updateProcessor;

  @PostConstruct
  public void init() {
    commandList.forEach(this::register);
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    updateProcessor.process(this, update);
  }
}
