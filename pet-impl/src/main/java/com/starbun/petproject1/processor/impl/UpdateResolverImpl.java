package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.model.UpdateExtended;
import com.starbun.petproject1.model.UpdateType;
import com.starbun.petproject1.processor.BasicProcessor;
import com.starbun.petproject1.processor.UpdateResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdateResolverImpl implements UpdateResolver {

  private Map<UpdateType, BasicProcessor<? extends BotApiObject>> processorMap;

  @Autowired
  public void makeProcessorMap(List<BasicProcessor<? extends BotApiObject>> processors) {
    processorMap = processors.stream()
        .collect(Collectors.toMap(BasicProcessor::getProcessingType, Function.identity()));
  }

  @Override
  public void process(AbsSender absSender, Update update) {
    UpdateExtended updateExt = new UpdateExtended(update);

    BasicProcessor<? extends BotApiObject> basicProcessor = processorMap.get(updateExt.getUpdateType());

    if (basicProcessor == null) {
      throw new NoImplementationException("Нет реализации процессора сообщений типа " + updateExt.getUpdateType());
    }
    basicProcessor.process(absSender, updateExt.getContent());
//    switch (updateExt.getUpdateType()) {
//      case MESSAGE -> messageProcessor.process(absSender, update.getMessage());
//      case CALLBACK_QUERY -> callbackQueryProcessor.process(absSender, update.getCallbackQuery());
//      case INLINE_QUERY, CHOSEN_INLINE_QUERY, EDITED_MESSAGE, CHANNEL_POST,
//        EDITED_CHANNEL_POST, SHIPPING_QUERY, PRE_CHECKOUT_QUERY, POLL, POLL_ANSWER,
//          MY_CHAT_MEMBER, CHAT_MEMBER, CHAT_JOIN_REQUEST -> {
//        throw new NoImplementationException("Нет реализации процессора сообщений типа " + updateExt.getUpdateType());
//      }
//      default -> throw new IllegalArgumentException("Пришёл неизвестный тип сообщения от телеги. Пора обновлять апи");
//    }
  }
}
