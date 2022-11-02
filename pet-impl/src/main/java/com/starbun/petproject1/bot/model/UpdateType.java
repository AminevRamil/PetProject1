package com.starbun.petproject1.bot.model;

/**
 * Перечисление возможных типов обновления получаемых от телеграм.
 * {@see org.telegram.telegrambots.meta.api.objects.Update}
 */
public enum UpdateType {
  MESSAGE,
  INLINE_QUERY,
  CHOSEN_INLINE_QUERY,
  CALLBACK_QUERY,
  EDITED_MESSAGE,
  CHANNEL_POST,
  EDITED_CHANNEL_POST,
  SHIPPING_QUERY,
  PRE_CHECKOUT_QUERY,
  POLL;
}
