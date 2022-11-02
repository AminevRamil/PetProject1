package com.starbun.petproject1.repository;

import com.starbun.petproject1.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUsersRepository extends JpaRepository<TelegramUser, Long> {

  boolean existsTelegramUsersByTgId(Long tgId);

  TelegramUser findFirstByTgId(Long id);
}
