package com.starbun.petproject1.service;

import com.starbun.petproject1.dto.State;
import com.starbun.petproject1.dto.TelegramUserDto;
import com.starbun.petproject1.dto.UserStateDto;
import com.starbun.petproject1.entity.TelegramUser;
import com.starbun.petproject1.entity.UserState;
import com.starbun.petproject1.mapper.TelegramUserMapper;
import com.starbun.petproject1.mapper.UserStateMapper;
import com.starbun.petproject1.repository.TelegramUsersRepository;
import com.starbun.petproject1.repository.UserStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramUserService {

  private final TelegramUsersRepository usersRepository;

  private final UserStateRepository userStateRepository;

  private final TelegramUserMapper telegramUserMapper;

  private final UserStateMapper userStateMapper;

  /**
   * Сохранение информации о новом пользователе
   */
  @Transactional
  public TelegramUserDto registerUser(User user) {
    if (usersRepository.existsTelegramUsersByTgId(user.getId())) {
      TelegramUser existingUser = usersRepository.findFirstByTgId(user.getId());
      updateInfoAboutUser(user, existingUser);
      return telegramUserMapper.mapToTelegramUserDto(existingUser);
    }
    TelegramUser telegramUser = telegramUserMapper.mapToNewTelegramUser(user);
    log.info("Регистрация пользователя {}", telegramUser);
    return telegramUserMapper.mapToTelegramUserDto(usersRepository.save(telegramUser));
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

  /**
   * Выгрузка текущего состояния работы пользователя с ботом и создание начального состояния, если
   * пользователь ранее с ботом не работал.
   * @param tgId идентификатор пользователя
   */
  @Transactional
  public UserStateDto getCurrentStatus(Long tgId) {
    Optional<UserState> userStateOpt = userStateRepository.findById(tgId);
    UserState userState = userStateOpt.orElse(createBeginState(tgId));
    return userStateMapper.toDto(userState);
  }

  /**
   * Создание новой записи о состоянии бота для конкретного пользователя
   * @param tgId идентификатор пользователя
   * @return состояние пользователя
   */
  private UserState createBeginState(Long tgId) {
    UserState newUserState = new UserState();
    newUserState.setTgId(tgId);
    newUserState.setCurrentState(State.BEGIN);
    return userStateRepository.save(newUserState);
  }
}
