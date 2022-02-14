package com.mgcqr.jest.dto.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mgcqr.jest.enumeration.WsResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MakeOfferResDto extends GameResponseAbsDto {

    @JsonProperty("offer_card_names")
    private List<String> offerCardNames;

    public MakeOfferResDto(){
        type = WsResponseType.MakeOffer;
    }
}
