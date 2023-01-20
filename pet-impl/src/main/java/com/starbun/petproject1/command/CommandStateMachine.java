package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.ActionResponse;

/**
 * Интерфейс машины состояний. Каждая машина состояний должна быть способна
 * выполнять действия, тем самым переключая внутреннее состояние.
 *
 * @param <S> Набор состояний, с которыми работает машина состояний.
 *            Состояния должны уметь работать с действиями <A>
 * @param <A> Набор действий, которые переключают состояния
 */
public interface CommandStateMachine<S extends CommandStates<A>, A extends CommandActions> {

  /**
   * Попытка выполнить заданное действие в условиях текущего состояния
   *
   * @param action заданное действие
   */
  ActionResponse performAction(A action);
}
