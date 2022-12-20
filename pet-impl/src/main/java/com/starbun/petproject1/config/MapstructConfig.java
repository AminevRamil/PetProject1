package com.starbun.petproject1.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * Конфигурация задающая тип компонентной модели и стратегию инъекций зависимостей для мапперов.
 */
@MapperConfig(
  componentModel = "spring",
  injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface MapstructConfig {

}
