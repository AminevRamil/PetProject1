package com.starbun.petproject1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {
  @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
  public LocalDateTime ping() {
     return LocalDateTime.now();
  }
}
