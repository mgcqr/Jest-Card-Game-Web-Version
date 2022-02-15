package com.mgcqr.jest.dto.ws;

import com.mgcqr.jest.enumeration.WsResponseType;
import lombok.Getter;

public abstract class GameResponseAbsDto {
    @Getter
    protected WsResponseType type;
}
