package com.starbun.petproject1.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

@MapperConfig(
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MapstructConfig {

}
