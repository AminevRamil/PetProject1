package com.starbun.petproject1.command;

import com.starbun.petproject1.command.debt.state.DebtActions;
import com.starbun.petproject1.command.debt.state.DebtState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Абстрактная реализация <b>CommandStateMachine</b> с вынесением общих полей и утильных методов.
 * Каждый экземпляр машины состояния закрепляется за конкретным экземпляром команды, описывает
 * её внутреннее состояние и ведёт учёт состояний.
 * Основные задачи машины состояний: <p>
 *   1. Переключение состояний команды, путём обработки поступающих действий <p>
 *   2. Хранение прикладных данных команды (в конкретных реализациях) <p>
 *   3. ...
 *
 * @param <S> Состояния с которыми работает машина состояний.
 *           Состояния должны уметь работать с действиями <b>A</b>
 * @param <A> Действия, которыми выполняет машина состояний
 */
public abstract class AbstractStateMachine<S extends CommandStates<A>, A extends CommandActions>
    implements CommandStateMachine<S, A> {

  /**
   * Карта соответствия состояния и обработчика этого состояния.
   */
  @Getter
  protected Map<S, CommandStateProcessor<S, A>> stateProcessors;
  /**
   * Идентификатор пользователя, с которым ассоциирована команда
   */
  @Getter
  @Setter
  protected Long userOwnerId;
  /**
   * Текущее состояние машины состояний
   */
  @Getter
  protected S currentState;

  @Override
  public void performAction(A action) {
    if (!stateProcessors.containsKey(currentState)) {
      throw new IllegalArgumentException("Обработчик не может обработать текущее состояние: " + currentState);
    }
    if (!currentState.getApplicableActions().contains(action)) {
      throw new UnsupportedOperationException(String.format(
          "Заданное действие %s не поддерживается в текущем состоянии %s", action.getCode(), currentState.getName()));
    }
    stateProcessors.get(currentState).process(action);
  }
}
