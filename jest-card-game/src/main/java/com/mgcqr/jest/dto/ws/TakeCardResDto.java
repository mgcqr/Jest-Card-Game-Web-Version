package com.mgcqr.jest.dto.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.WsResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeCardResDto extends GameResponseAbsDto{
    //user id to card names
    @JsonProperty("available_offers ")
    private Map<String, String> availableOffers;

    public TakeCardResDto(Map<String, String> availableOffers){
        type = WsResponseType.TakeCard;
        this.availableOffers = availableOffers;
    }
}
