package com.starbun.petproject1.mapper;

import com.starbun.petproject1.config.MapstructConfig;
import com.starbun.petproject1.entity.TelegramUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(config = MapstructConfig.class)
public interface TelegramUserMapper {

  @Mapping(target = "tgId", source = "id")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "isActual", ignore = true)
  public abstract TelegramUser mapToNewTelegramUser(User user);
}
