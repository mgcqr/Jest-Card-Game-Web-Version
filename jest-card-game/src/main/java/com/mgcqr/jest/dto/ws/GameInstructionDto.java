package com.mgcqr.jest.dto.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.InstructionType;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class GameInstructionDto {
    private InstructionType type;
    private String userId;
    private String token;
    @JsonProperty("card_name")
    private String cardName;
    @JsonProperty("target_user_id")
    private String targetUserId;
    @JsonProperty("is_face_up")
    private Boolean isFaceUp;

}
