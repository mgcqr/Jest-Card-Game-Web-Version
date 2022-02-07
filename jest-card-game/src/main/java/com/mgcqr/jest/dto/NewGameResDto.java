package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewGameResDto {
    @JsonProperty("game_id")
    private String gameId;
}
