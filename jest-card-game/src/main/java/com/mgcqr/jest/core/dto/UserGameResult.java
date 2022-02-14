package com.mgcqr.jest.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.core.stuff.Card;
import lombok.Data;

import java.util.List;

@Data
public class UserGameResult {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("jest_card_names")
    private List<String> jestCardNames;
    private int score;
}
