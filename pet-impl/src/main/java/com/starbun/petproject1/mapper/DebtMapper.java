package com.starbun.petproject1.mapper;

import com.starbun.petproject1.config.MapstructConfig;
import com.starbun.petproject1.dto.DebtDto;
import com.starbun.petproject1.entity.Debt;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface DebtMapper {

  DebtDto toDto(Debt debt);
}
