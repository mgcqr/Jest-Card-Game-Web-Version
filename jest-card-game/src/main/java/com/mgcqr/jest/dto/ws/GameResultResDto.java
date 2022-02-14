package com.mgcqr.jest.dto.ws;

import com.mgcqr.jest.core.dto.UserGameResult;
import com.mgcqr.jest.enumeration.WsResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameResultResDto extends GameResponseAbsDto {
    private List<UserGameResult> results;
    public GameResultResDto(){
        type = WsResponseType.Result;
    }
}
