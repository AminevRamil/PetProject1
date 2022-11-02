package com.starbun.petproject1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "telegram_users")
public class TelegramUser {
  @Id
  private Long id;

  @Column(name = "tgId")
  private Long tgId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username")
  private String username;

  @Column(name = "is_actual")
  private String isActual;
}
