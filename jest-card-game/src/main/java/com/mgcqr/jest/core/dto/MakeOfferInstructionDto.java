package com.mgcqr.jest.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MakeOfferInstructionDto extends MailBoxDto {
    private int choice;
}