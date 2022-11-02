package com.starbun.petproject1.service;

import com.starbun.petproject1.entity.TelegramUser;
import com.starbun.petproject1.mapper.TelegramUserMapper;
import com.starbun.petproject1.repository.TelegramUsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  public TelegramUser registerUser(User user) {
    if (repository.existsTelegramUsersByTgId(user.getId())) {
      return repository.findFirstByTgId(user.getId());
    }
    TelegramUser telegramUser = mapper.mapToNewTelegramUser(user);
    log.info("Регистрация пользователя {}", telegramUser);
    return repository.save(telegramUser);
  }
}
