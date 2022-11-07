package com.starbun.petproject1.mapper;

import com.starbun.petproject1.config.MapstructConfig;
import com.starbun.petproject1.dto.UserStateDto;
import com.starbun.petproject1.entity.UserState;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface UserStateMapper {

  UserStateDto toDto(UserState debt);
}
