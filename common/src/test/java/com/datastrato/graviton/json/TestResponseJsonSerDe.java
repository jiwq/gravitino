package com.datastrato.graviton.json;

import com.datastrato.graviton.dto.AuditDTO;
import com.datastrato.graviton.dto.MetalakeDTO;
import com.datastrato.graviton.dto.responses.BaseResponse;
import com.datastrato.graviton.dto.responses.ErrorType;
import com.datastrato.graviton.dto.responses.MetalakeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestResponseJsonSerDe {

  @Test
  public void testBaseResponseSerDe() throws JsonProcessingException {
    BaseResponse response = new BaseResponse();
    String serJson = JsonUtils.objectMapper().writeValueAsString(response);
    BaseResponse deserResponse = JsonUtils.objectMapper().readValue(serJson, BaseResponse.class);
    Assertions.assertEquals(response, deserResponse);

    BaseResponse response1 = BaseResponse.error(ErrorType.INTERNAL_ERROR, "error");
    String serJson1 = JsonUtils.objectMapper().writeValueAsString(response1);
    BaseResponse deserResponse1 = JsonUtils.objectMapper().readValue(serJson1, BaseResponse.class);
    Assertions.assertEquals(response1, deserResponse1);
  }

  @Test
  public void testMetalakeResponseSerDe() throws JsonProcessingException {
    MetalakeDTO metalake =
        new MetalakeDTO.Builder()
            .withName("metalake")
            .withComment("comment")
            .withProperties(ImmutableMap.of("key", "value"))
            .withAudit(
                AuditDTO.builder().withCreator("creator").withCreateTime(Instant.now()).build())
            .build();

    MetalakeResponse response = new MetalakeResponse(metalake);
    String serJson = JsonUtils.objectMapper().writeValueAsString(response);
    MetalakeResponse deserResponse =
        JsonUtils.objectMapper().readValue(serJson, MetalakeResponse.class);
    Assertions.assertEquals(response, deserResponse);
  }
}