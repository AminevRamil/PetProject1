package com.starbun.petproject1.mapper;

import com.starbun.petproject1.dto.UserStateDto;
import com.starbun.petproject1.entity.UserState;
import org.mapstruct.Mapper;

@Mapper
public interface UserStateMapper {

  UserStateDto toDto(UserState debt);
}
