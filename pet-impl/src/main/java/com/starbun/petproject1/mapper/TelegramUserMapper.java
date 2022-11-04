package com.starbun.petproject1.mapper;

import com.starbun.petproject1.config.MapstructConfig;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.entity.TelegramUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(config = MapstructConfig.class)
public interface TelegramUserMapper {

  @Mapping(target = "tgId", source = "id")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "username", source = "userName")
  @Mapping(target = "isActual", expression = "java(true)")
  TelegramUser mapToNewTelegramUser(User user);

  TelegramUserDto mapToTelegramUserDto(TelegramUser tgUser);
}
