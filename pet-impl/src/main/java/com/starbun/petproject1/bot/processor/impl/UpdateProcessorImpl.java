package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.model.UpdateExtended;
import com.starbun.petproject1.bot.processor.CallbackQueryProcessor;
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
  private final CallbackQueryProcessor callbackQueryProcessor;

  @Override
  public void process(AbsSender absSender, Update update) {
    UpdateExtended updateExt = new UpdateExtended(update);
    switch (updateExt.getUpdateType()) {
      case MESSAGE -> messageProcessor.process(absSender, update.getMessage());
      case CALLBACK_QUERY -> callbackQueryProcessor.process(absSender, update.getCallbackQuery());
      case INLINE_QUERY, CHOSEN_INLINE_QUERY, EDITED_MESSAGE, CHANNEL_POST,
        EDITED_CHANNEL_POST, SHIPPING_QUERY, PRE_CHECKOUT_QUERY, POLL, POLL_ANSWER,
        PRIVATE_CHAT_MEMBER, CHAT_MEMBER, CHAT_JOIN_REQUEST -> {
        throw new NoImplementationException("Нет реализации процессора сообщений типа " + updateExt.getUpdateType());
      }
      default -> throw new IllegalArgumentException("Пришёл неизвестный тип сообщения от телеги. Пора обновлять апи");
    }
  }
}
