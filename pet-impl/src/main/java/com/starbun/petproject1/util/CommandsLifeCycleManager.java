package com.starbun.petproject1.util;

import com.starbun.petproject1.command.BasicCommand;
import com.starbun.petproject1.command.start.StartCommand;
import com.starbun.petproject1.service.CommandFactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  /**
   * Название команды создаваемой по умолчанию
   * TODO Сделать какое-то хранилище названий команд, интерфейс или класс с константами.
   */
  private static final String DEFAULT_COMMAND = "start";

  private final Map<Long, BasicCommand> userToCommandMap = new HashMap<>();

  private final CommandFactoryService commandFactory;

  /**
   * Планировщик для завершения работы с командами по таймауту.
   * TODO сделать возможность задания периода очистки в yml.
   * TODO volatile на мапу, чтоб команда не удалилась, пока выполняются другие методы?
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
   * Если предыдущая сессия с пользователем завершена по таймауту, то создаёт команду по умолчанию.
   * Если предыдущая сессия с пользователем НЕ завершена, то пытается закрыть предыдущую сессию и создать новую.
   *
   * @param userId      внутренний идентификатор пользователя
   * @param commandName команда, которую задал пользователь
   * @return команду с которой работает пользователь
   */
  public BasicCommand getCommandByUserId(Long userId, String commandName) {
    BasicCommand command = userToCommandMap.containsKey(userId) ? getUserCommandAndUpdateExpiryDate(userId) : getDefaultCommand(userId);
    return command.getCommandIdentifier().equals(commandName) ? command : tryToCloseOldCommandAndGetNew(command, commandName, userId);
  }

  public BasicCommand getCommandByUserId(Long userId) {
    return getCommandByUserId(userId, null);
  }

  /**
   * Попытка закрытия старой команды и создания новой команды.
   * @implNote Недоделана. В любом случае создаёт новую команду
   */
  private BasicCommand tryToCloseOldCommandAndGetNew(BasicCommand command, String commandName, Long userId) {
    if (command.getCurrentState().getId().equals(Integer.MAX_VALUE)) {
      BasicCommand newCommand = commandFactory.createCommand(commandName);
      storeNewCommand(newCommand, userId);
      return newCommand;
    } else {
      // TODO Переделать под попытку завершения старой команды
      // TODO Например, выплюнуть ошибку, что не завершена работа с предыдущей командой.
      BasicCommand newCommand = commandFactory.createCommand(commandName);
      storeNewCommand(newCommand, userId);
      return newCommand;
    }
  }

  /**
   * Достаёт команду, с которой работает пользователь и обновляет её "срок годности"
   */
  private BasicCommand getUserCommandAndUpdateExpiryDate(Long userId) {
    BasicCommand basicCommand = userToCommandMap.get(userId);
    basicCommand.setExpiryDate(LocalDateTime.now().plusMinutes(TIME_TO_LIVE));
    return basicCommand;
  }

  /**
   * Создание стартовой команды для пользователя
   */
  private BasicCommand getDefaultCommand(Long userId) {
    BasicCommand command = commandFactory.createCommand(DEFAULT_COMMAND);
    storeNewCommand(command, userId);
    return command;
  }

  /**
   * Сохраняет новую текущую команду для пользователя и прописывает идентификатор пользователя в команду
   */
  public void storeNewCommand(BasicCommand command, Long userId) {
    command.setUserOwnerId(userId);
    userToCommandMap.put(userId, command);
  }
}
