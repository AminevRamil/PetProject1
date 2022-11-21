package com.starbun.petproject1.bot.processor.impl;

import com.starbun.petproject1.bot.command.DebtCommand;
import com.starbun.petproject1.bot.processor.CallbackQueryProcessor;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.service.InlineCallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class CallbackQueryProcessorImpl implements CallbackQueryProcessor {

  private final InlineCallbackService inlineCallbackService;

  private final DebtCommand debtCommand;

  @Override
  public void process(AbsSender absSender, CallbackQuery message) {
    InlineButtonInfo buttonData = inlineCallbackService.getInlineData(message.getData());
    switch (buttonData.getBAction()) {

      case CREATE_DEBT, CHANGE_DEBTOR, CHANGE_DEBT_SUBJECT, CHANGE_DATE  -> {
        debtCommand.executeInlineButton(absSender, message, buttonData);
      }

    }
  }
}
