package com.starbun.petproject1.bot.model;

import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Расширение класса {@link Update} для повышения удобства обработки.
 * 1. Инкапсулирует проверку has методов, предоставляя енам для гарантированного изъятия информации {@link UpdateType}
 * 2. ...
 */
@Getter
@ToString
public class UpdateExtended extends Update {

  private UpdateType updateType;

  public UpdateExtended(Update update) {
    int safeCounter = 0;
    if (update.hasMessage()) {
      updateType = UpdateType.MESSAGE;
      safeCounter++;
    }
    if (update.hasInlineQuery()) {
      updateType = UpdateType.INLINE_QUERY;
      safeCounter++;
    }
    if (update.hasChosenInlineQuery()) {
      updateType = UpdateType.CHOSEN_INLINE_QUERY;
      safeCounter++;
    }
    if (update.hasCallbackQuery()) {
      updateType = UpdateType.CALLBACK_QUERY;
      safeCounter++;
    }
    if (update.hasEditedMessage()) {
      updateType = UpdateType.EDITED_MESSAGE;
      safeCounter++;
    }
    if (update.hasChannelPost()) {
      updateType = UpdateType.CHANNEL_POST;
      safeCounter++;
    }
    if (update.hasEditedMessage()) {
      updateType = UpdateType.EDITED_CHANNEL_POST;
      safeCounter++;
    }
    if (update.hasShippingQuery()) {
      updateType = UpdateType.SHIPPING_QUERY;
      safeCounter++;
    }
    if (update.hasPreCheckoutQuery()) {
      updateType = UpdateType.PRE_CHECKOUT_QUERY;
      safeCounter++;
    }
    if (update.hasPoll()) {
      updateType = UpdateType.POLL;
      safeCounter++;
    }
    if (safeCounter != 1) {
      throw new IllegalArgumentException("Пришло несколько типов сообщений в одном обновлении. Согласно документации, такого быть не должно");
    }
  }
}
