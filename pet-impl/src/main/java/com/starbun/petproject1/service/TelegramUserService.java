package com.starbun.petproject1.service;

import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.entity.TelegramUser;
import com.starbun.petproject1.mapper.TelegramUserMapper;
import com.starbun.petproject1.repository.TelegramUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

  private final TelegramUsersRepository repository;

  private final TelegramUserMapper mapper;

  /**
   * Сохранение информации о новом пользователе
   */
  @Transactional
  public TelegramUserDto registerUser(User user) {
    if (repository.existsTelegramUsersByTgId(user.getId())) {
      TelegramUser existingUser = repository.findFirstByTgId(user.getId());
      updateInfoAboutUser(user, existingUser);
      return mapper.mapToTelegramUserDto(existingUser);
    }
    TelegramUser telegramUser = mapper.mapToNewTelegramUser(user);
    log.info("Регистрация пользователя {}", telegramUser);
    return mapper.mapToTelegramUserDto(repository.save(telegramUser));
  }

  /**
   * Обновление информации о пользователе, в случае если он поменял свои данные между отдельными обращениями к боту
   * @param user данные о пользователе, пришедшие из телеграм
   * @param existingUser данные о пользователе хранящиеся у бота
   */
  private void updateInfoAboutUser(User user, TelegramUser existingUser) {
    if (!StringUtils.equals(existingUser.getUsername(), user.getUserName())) {
      existingUser.setUsername(user.getUserName());
    }
    if (StringUtils.equals(existingUser.getFirstName(), user.getFirstName())) {
      existingUser.setFirstName(user.getFirstName());
    }
    if (StringUtils.equals(existingUser.getLastName(), user.getLastName())) {
      existingUser.setLastName(user.getLastName());
    }
    existingUser.setIsActual(true);
  }
}
