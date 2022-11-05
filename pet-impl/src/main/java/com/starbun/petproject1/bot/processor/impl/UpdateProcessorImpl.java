package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.model.UpdateExtended;
import com.starbun.petproject1.bot.processor.MessageProcessor;
import com.starbun.petproject1.bot.processor.UpdateProcessor;
import com.starbun.petproject1.exception.NoImplementationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class UpdateProcessorImpl implements UpdateProcessor {

  private final MessageProcessor messageProcessor;

  @Override
  public void process(AbsSender absSender, Update update) {
    UpdateExtended updateExt = new UpdateExtended(update);
    switch (updateExt.getUpdateType()) {
      case MESSAGE -> messageProcessor.processMessage(absSender, update.getMessage());
      case INLINE_QUERY, CHOSEN_INLINE_QUERY, CALLBACK_QUERY, EDITED_MESSAGE, CHANNEL_POST,
        EDITED_CHANNEL_POST, SHIPPING_QUERY, PRE_CHECKOUT_QUERY, POLL, POLL_ANSWER,
        PRIVATE_CHAT_MEMBER, CHAT_MEMBER, CHAT_JOIN_REQUEST -> {
        throw new NoImplementationException("Нет реализации процессора сообщений типа " + updateExt.getUpdateType());
      }
      default -> throw new IllegalArgumentException("Пришёл неизвестный тип сообщения от телеги. Пора обновлять апи");
    }
  }
}
