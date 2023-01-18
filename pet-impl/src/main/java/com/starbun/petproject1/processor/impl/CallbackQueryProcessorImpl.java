package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.model.UpdateType;
import com.starbun.petproject1.processor.CallbackQueryProcessor;
import com.starbun.petproject1.dto.InlineButtonInfo;
import com.starbun.petproject1.service.InlineCallbackService;
import com.starbun.petproject1.service.TelegramUserService;
import com.starbun.petproject1.util.CommandsLifeCycleManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@RequiredArgsConstructor
public class CallbackQueryProcessorImpl implements CallbackQueryProcessor {

  @Getter
  private final UpdateType processingType = UpdateType.CALLBACK_QUERY;

  private final InlineCallbackService inlineCallbackService;

  private final CommandsLifeCycleManager commandsLifeCycleController;

  private final TelegramUserService telegramUserService;

  /**
   * Тут только обработка инлайн-кнопок
   */
  @Override
  public void process(AbsSender absSender, CallbackQuery query) {
    InlineButtonInfo buttonInfo = new InlineButtonInfo(query.getData());

    TelegramUserDto telegramUserDto = telegramUserService.registerOrFetchUser(query.getFrom());
    Long userIdOfButtonPresser = telegramUserDto.getId();
    Long userIdOfButtonOwner = buttonInfo.getUserId();

    if (userIdOfButtonPresser.equals(userIdOfButtonOwner)) {
      AbstractCommand command = commandsLifeCycleController.getCommandByUserId(userIdOfButtonPresser);
      command.executeInlineButton(absSender, query);
    } else {
      /**
       * Либо проверить, что комманда "общая" и кто угодно может тыкать,
       * Либо написать что пользователь не может тыкать чужие кнопки,
       * Либо Ничего не сделать
       */
    }
  }
}
