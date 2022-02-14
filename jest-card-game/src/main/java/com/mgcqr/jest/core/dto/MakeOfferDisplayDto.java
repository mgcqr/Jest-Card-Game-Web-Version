package com.mgcqr.jest.core.dto;

import com.mgcqr.jest.core.stuff.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class MakeOfferDisplayDto extends MailBoxAbsDto {
    //key is user database id
    private Map<String, Card[]> userOffers;
}
