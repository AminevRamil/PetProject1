package com.starbun.petproject1.model;

import com.starbun.petproject1.exception.TelegramApiMismatchException;
import lombok.Getter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
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

  private BotApiObject content;

  public <C extends BotApiObject> C getContent(){
    return (C)content;
  }

  public UpdateExtended(Update update) {
    int safeCounter = 0;
    if (update.hasMessage()) {
      updateType = UpdateType.MESSAGE;
      content = update.getMessage();
      safeCounter++;
    }
    if (update.hasInlineQuery()) {
      updateType = UpdateType.INLINE_QUERY;
      content = update.getInlineQuery();
      safeCounter++;
    }
    if (update.hasChosenInlineQuery()) {
      updateType = UpdateType.CHOSEN_INLINE_QUERY;
      content = update.getChosenInlineQuery();
      safeCounter++;
    }
    if (update.hasCallbackQuery()) {
      updateType = UpdateType.CALLBACK_QUERY;
      content = update.getCallbackQuery();
      safeCounter++;
    }
    if (update.hasEditedMessage()) {
      updateType = UpdateType.EDITED_MESSAGE;
      content = update.getEditedMessage();
      safeCounter++;
    }
    if (update.hasChannelPost()) {
      updateType = UpdateType.CHANNEL_POST;
      content = update.getChannelPost();
      safeCounter++;
    }
    if (update.hasEditedChannelPost()) {
      updateType = UpdateType.EDITED_CHANNEL_POST;
      content = update.getEditedChannelPost();
      safeCounter++;
    }
    if (update.hasShippingQuery()) {
      updateType = UpdateType.SHIPPING_QUERY;
      content = update.getShippingQuery();
      safeCounter++;
    }
    if (update.hasPreCheckoutQuery()) {
      updateType = UpdateType.PRE_CHECKOUT_QUERY;
      content = update.getPreCheckoutQuery();
      safeCounter++;
    }
    if (update.hasPoll()) {
      updateType = UpdateType.POLL;
      content = update.getPoll();
      safeCounter++;
    }
    if (update.hasPollAnswer()) {
      updateType = UpdateType.POLL_ANSWER;
      content = update.getPollAnswer();
      safeCounter++;
    }
    if (update.hasMyChatMember()) {
      updateType = UpdateType.MY_CHAT_MEMBER;
      content = update.getMyChatMember();
      safeCounter++;
    }
    if (update.hasChatMember()) {
      updateType = UpdateType.CHAT_MEMBER;
      content = update.getChatMember();
      safeCounter++;
    }
    if (update.hasChatJoinRequest()) {
      updateType = UpdateType.CHAT_JOIN_REQUEST;
      content = update.getChatJoinRequest();
      safeCounter++;
    }
    if (safeCounter > 1) {
      throw new TelegramApiMismatchException("Пришло несколько типов сообщений в одном обновлении. Согласно документации, такого быть не должно");
    } else if (safeCounter == 0) {
      throw new IllegalArgumentException("Пришёл неизвестный тип сообщения от телеги. Пора обновлять апи");
    }
  }
}
