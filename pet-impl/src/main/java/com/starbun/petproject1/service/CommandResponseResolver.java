package com.starbun.petproject1.service;

import com.starbun.petproject1.dto.CommandResponse;

public interface CommandResponseResolver {

  Boolean sendToTelegram(CommandResponse commandResponse);
}
