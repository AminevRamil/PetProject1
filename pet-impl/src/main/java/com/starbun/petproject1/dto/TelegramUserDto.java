package com.starbun.petproject1.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class TelegramUserDto {
  private Long id;
  private Long tgId;
  private String firstName;
  private String lastName;
  private String username;

  public String getActualUsername() {
    if (StringUtils.isNotEmpty(username)) {
      return "@" + username;
    } else if (StringUtils.isNotEmpty(lastName)) {
      return lastName + " " + firstName;
    } else {
      return firstName;
    }
  }
}
