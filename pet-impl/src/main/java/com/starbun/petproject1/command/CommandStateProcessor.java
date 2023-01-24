package com.starbun.petproject1.command;

import com.starbun.petproject1.dto.ProcessorRequest;
import com.starbun.petproject1.dto.StateProcessorResponse;

/**
 * Интерфейс обработчика состояний. Каждый обработчик должен быть
 * способен обработать поступающее действие. Иначе выбросить ошибку
 * или заполнить сообщение с отказом от выполнения действия.
 * В результате обработчик должен ещё решить, нужно ли переключать
 * состояние или нет.
 *
 * @param <S> Набор состояний, которые обрабатывает процессор.
 *            Состояния должны уметь работать с действиями <A>
 * @param <A> Набор действий, которые переключают состояния
 */
public interface CommandStateProcessor<S extends CommandStates<A>, A extends CommandActions> {

  /**
   * Попытка обработать текущее состояние реализуя заданное действие
   *
   * @param request действие к исполнению
   * @return результат отработки действия для последующего применения
   */
  StateProcessorResponse process(ProcessorRequest<A> request);
}
