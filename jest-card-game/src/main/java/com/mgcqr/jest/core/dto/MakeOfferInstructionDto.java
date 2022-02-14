package com.mgcqr.jest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class MakeOfferInstructionDto extends MailBoxAbsDto {
    //index of card chosen to be face up in offer(Card[])
    private int choice;
}
