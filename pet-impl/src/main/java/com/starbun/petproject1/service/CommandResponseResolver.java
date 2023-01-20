package com.starbun.petproject1.service;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

public interface CommandResponseResolver {

  <T extends Serializable> Boolean sendToTelegram(BotApiMethod<T> method);
}
