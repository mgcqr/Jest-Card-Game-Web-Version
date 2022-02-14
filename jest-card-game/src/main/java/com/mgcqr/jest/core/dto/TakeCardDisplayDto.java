package com.mgcqr.jest.core.dto;

import com.mgcqr.jest.core.stuff.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeCardDisplayDto extends MailBoxDto {

    private String userId;
    //key is user database id
    //only contains offers remain 2 cards
    private Map<String, Card[]> userOffers;

}
