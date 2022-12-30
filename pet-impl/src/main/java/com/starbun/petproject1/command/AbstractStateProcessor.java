package com.starbun.petproject1.command;

import lombok.Getter;

/**
 *  Абстрактная реализация обработчика состояний. Каждый обработчик должен работать с
 *  одним конкретным состоянием, обрабатывать его действия и TODO выдавать ответ.
 *
 *
 * @param <S> Набор состояний, которые обрабатывает процессор.
 *            Состояния должны уметь работать с действиями <A>
 * @param <A> Набор действий, которые переключают состояния
 */
public abstract class AbstractStateProcessor<S extends CommandStates<A>, A extends CommandActions>
    implements CommandStateProcessor<S, A> {

  /**
   * Одно конкретное состояние, которое обрабатывает реализуемый процессор.
   */
  @Getter
  protected S processingState;
}
