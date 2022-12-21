package com.starbun.petproject1.util;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.command.start.StartCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandsLifeCycleManager {

  // TODO сделать задание через yml (Если можно задать статическую константу так)
  /**
   * Тайм-аут для ожидания ответа от пользователя. В минутах
   */
  public static final Long TIME_TO_LIVE = 60L;

  private final Map<Long, BasicCommand> userToCommandMap = new HashMap<>();

  private final ObjectFactory<StartCommand> startCommandFactory;

  /**
   * Планировщик для завершения работы с командами по таймауту.
   * TODO сделать возможность задания периода очистки в yml
   */
  @Scheduled(cron = "0 * * * * *")
  public void cleanupOldCommands() {
    LocalDateTime now = LocalDateTime.now();
    List<Map.Entry<Long, BasicCommand>> commandsToDelete = userToCommandMap.entrySet().stream()
        .filter(entry -> entry.getValue().getExpiryDate().isBefore(now))
        .toList();
    if (!CollectionUtils.isEmpty(commandsToDelete)) {
      commandsToDelete.stream().map(Map.Entry::getKey).forEach(userToCommandMap::remove);
      // TODO Проверить, что GC действительно сам уничтожит эти прототипы самостоятельно
      log.info("Удалено команд по таймауту: {}", commandsToDelete.size());
    }
  }

  /**
   * Достаёт команду с которой работает пользователь в данный момент.
   * Если предыдущая сессия с пользователем завершена по таймауту, то создаёт команду по умолчанию
   * @param userId внутренний идентификатор пользователя
   * @return команду с которой работает пользователь
   */
  public BasicCommand getCommandByUserId(Long userId) {
    return userToCommandMap.containsKey(userId) ? getUserCommandAndUpdateExpiryDate(userId) : getNewStartCommand(userId);
  }

  /**
   * Достаёт команду, с которой работает пользователь и обновляет её "срок годности"
   * @param userId внутренний идентификатор пользователя
   * @return команду с которой работает пользователь
   */
  private BasicCommand getUserCommandAndUpdateExpiryDate(Long userId) {
    BasicCommand basicCommand = userToCommandMap.get(userId);
    basicCommand.setExpiryDate(LocalDateTime.now().plusMinutes(TIME_TO_LIVE));
    return basicCommand;
  }

  /**
   * Создание стартовой команды для пользователя
   * @param userId внутренний идентификатор пользователя
   * @return стартовая команда
   */
  private StartCommand getNewStartCommand(Long userId) {
    StartCommand newStartCommand = startCommandFactory.getObject();
    newStartCommand.setUserOwnerId(userId);
    userToCommandMap.put(userId, newStartCommand);
    return newStartCommand;
  }

  /**
   * Сохраняет новую текущую команду для пользователя
   */
  public void storeNewCommand(BasicCommand command, Long userId) {
    userToCommandMap.put(userId, command);
  }
}
