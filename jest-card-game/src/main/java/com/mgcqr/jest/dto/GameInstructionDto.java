package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.InstructionType;
import lombok.Data;

@Data
public class GameInstructionDto {
    private InstructionType type;
    private String token;
    @JsonProperty("card_name")
    private String cardName;
    @JsonProperty("target_user_id")
    private String targetUserId;

}
