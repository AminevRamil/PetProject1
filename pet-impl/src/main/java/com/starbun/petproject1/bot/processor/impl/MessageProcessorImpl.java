package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.processor.MessageProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageProcessorImpl implements MessageProcessor {
  @Override
  public void processMessage(Message message) {
    System.out.println(message.toString());
  }
}
