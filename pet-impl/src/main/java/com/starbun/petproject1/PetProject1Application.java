package com.starbun.petproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PetProject1Application {

  public static void main(String[] args) {
    SpringApplication.run(PetProject1Application.class, args);
  }
}
