package com.starbun.petproject1.bot;

import com.starbun.petproject1.bot.processor.UpdateProcessor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StarBunBot extends TelegramLongPollingBot {

  @Getter
  @Value("${telegram.bot.token}")
  private String botToken;

  @Getter
  @Value("${telegram.bot.name}")
  private String botUsername;

  @Autowired
  private UpdateProcessor updateProcessor;

  @Override
  public void onUpdateReceived(Update update) {
    updateProcessor.process(update);
  }
}
