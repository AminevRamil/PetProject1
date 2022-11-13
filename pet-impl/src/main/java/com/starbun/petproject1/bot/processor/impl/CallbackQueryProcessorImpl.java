package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.processor.CallbackQueryProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class CallbackQueryProcessorImpl implements CallbackQueryProcessor {

  @Override
  public void process(AbsSender absSender, CallbackQuery message) {

  }
}
