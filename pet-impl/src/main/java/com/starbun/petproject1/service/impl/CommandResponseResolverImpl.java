package com.starbun.petproject1.service.impl;

import com.starbun.petproject1.service.CommandResponseResolver;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

@Service
public class CommandResponseResolverImpl implements CommandResponseResolver {
  @Override
  public <T extends Serializable> Boolean sendToTelegram(BotApiMethod<T> method) {
    return null;
  }
}
