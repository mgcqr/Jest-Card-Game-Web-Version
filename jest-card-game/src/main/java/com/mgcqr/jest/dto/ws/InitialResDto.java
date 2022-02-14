package com.mgcqr.jest.dto.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.WsResponseType;
import com.mgcqr.jest.model.RuntimeUserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InitialResDto extends GameResponseAbsDto {

    @JsonProperty("left_user")
    private RuntimeUserInfo leftUser;
    @JsonProperty("right_user")
    private RuntimeUserInfo rightUser;
    private List<String> trophy;

    public InitialResDto(){
        type = WsResponseType.Initial;
    }
}
