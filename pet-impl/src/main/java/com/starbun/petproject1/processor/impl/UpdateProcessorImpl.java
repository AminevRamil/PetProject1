package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.model.UpdateExtended;
import com.starbun.petproject1.processor.BasicProcessor;
import com.starbun.petproject1.processor.CallbackQueryProcessor;
import com.starbun.petproject1.processor.MessageProcessor;
import com.starbun.petproject1.processor.UpdateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateProcessorImpl implements UpdateProcessor {

  private final MessageProcessor messageProcessor;
  private final CallbackQueryProcessor callbackQueryProcessor;

  @Autowired
  public void makeProcessorMap(List<BasicProcessor<? extends BotApiObject>> processors) {
    //
  }

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
