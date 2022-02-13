package com.mgcqr.jest.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MakeOfferDto extends MailBoxDto {
    private int cardNum;
}
