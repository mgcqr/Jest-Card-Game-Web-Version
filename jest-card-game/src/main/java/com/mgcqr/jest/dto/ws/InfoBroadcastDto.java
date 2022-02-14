package com.mgcqr.jest.dto.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.core.enumeration.Operation;
import com.mgcqr.jest.enumeration.WsResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InfoBroadcastDto extends GameResponseAbsDto {

    private Operation operation;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("card_name")
    private String cardName;
    @JsonProperty("target_user_id")
    private String targetUserId;


    public InfoBroadcastDto(){
        type = WsResponseType.Info;
    }
}
