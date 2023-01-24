package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.exception.NoImplementationException;
import com.starbun.petproject1.model.UpdateExtended;
import com.starbun.petproject1.model.UpdateType;
import com.starbun.petproject1.processor.BotApiObjectProcessor;
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

  private Map<UpdateType, BotApiObjectProcessor<? extends BotApiObject>> processorMap;

  @Autowired
  public void makeProcessorMap(List<BotApiObjectProcessor<? extends BotApiObject>> processors) {
    processorMap = processors.stream()
        .collect(Collectors.toMap(BotApiObjectProcessor::getProcessingType, Function.identity()));
  }

  @Override
  public void process(AbsSender absSender, Update update) {
    UpdateExtended updateExt = new UpdateExtended(update);
    var concreteProcessor = processorMap.get(updateExt.getUpdateType());

    if (concreteProcessor == null) {
      throw new NoImplementationException("Нет реализации процессора сообщений типа " + updateExt.getUpdateType());
    }
    concreteProcessor.process(absSender, updateExt.getContent());
  }
}
