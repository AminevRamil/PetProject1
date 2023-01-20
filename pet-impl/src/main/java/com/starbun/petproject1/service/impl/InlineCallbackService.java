package com.starbun.petproject1.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbun.petproject1.dto.InlineButtonInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InlineCallbackService {

  private final ObjectMapper jsonMapper;

  public InlineButtonInfo getInlineData(String data){
    try {
      return jsonMapper.readValue(data, InlineButtonInfo.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
