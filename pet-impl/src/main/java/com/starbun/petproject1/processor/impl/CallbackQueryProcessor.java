package com.starbun.petproject1.processor.impl;

import com.starbun.petproject1.command.AbstractCommand;
import com.starbun.petproject1.dto.CommandResponse;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.model.UpdateType;
import com.starbun.petproject1.processor.BotApiObjectProcessor;
import com.starbun.petproject1.service.CommandResponseResolver;
import com.starbun.petproject1.service.impl.TelegramUserService;
import com.starbun.petproject1.util.CommandsLifeCycleManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Процессор обрабатывающий нажатия инлайн-кнопок
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallbackQueryProcessor implements BotApiObjectProcessor<CallbackQuery> {

  @Getter
  private final UpdateType processingType = UpdateType.CALLBACK_QUERY;
  private final CommandsLifeCycleManager commandsLifeCycleController;
  private final TelegramUserService telegramUserService;
  private final CommandResponseResolver commandResponseResolver;

  /**
   * Тут только обработка инлайн-кнопок
   */
  @Override
  public void process(AbsSender absSender, CallbackQuery query) {
    log.info("Нажата кнопка пользователем {}", query.getFrom().getId());
    TelegramUserDto telegramUserDto = telegramUserService.registerOrFetchUser(query.getFrom());

    // TODO Процессор не должен решать, "общая" ли сейчас команда или нет. Должна знать сама команда.
    //if (userIdOfButtonPresser.equals(userIdOfButtonOwner)) {
    AbstractCommand command = commandsLifeCycleController.getCommandByUserId(telegramUserDto.getId());
    CommandResponse commandResponse = command.executeInlineButton(absSender, query);
    commandResponseResolver.sendToTelegram(commandResponse);
  }
}
