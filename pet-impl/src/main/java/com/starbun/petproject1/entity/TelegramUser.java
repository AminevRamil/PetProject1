package com.starbun.petproject1.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@Table(name = "telegram_users")
public class TelegramUser {
  @Id
  @GeneratedValue(strategy = IDENTITY)
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
  private Boolean isActual;
}
