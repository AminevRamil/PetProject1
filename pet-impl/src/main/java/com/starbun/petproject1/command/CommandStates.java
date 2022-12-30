package com.starbun.petproject1.command;

import java.util.List;

/**
 * Интерфейс предназначенный для енамов, в которых перечисленны все состояния команды.
 * Описывает контракт и свойства, которыми должны обладать все состояний команд бота.
 * Каждое состояние должно хранить в себе список действий, которые выполнимы в
 * этом самом состоянии.
 *
 * @param <A> список действий, для работы с состояниями
 */
public interface CommandStates<A extends CommandActions> {
  Integer getCode();
  String getName();
  List<A> getApplicableActions();
}
