package com.mgcqr.jest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TakeCardNameDisplayDto extends MailBoxAbsDto {
    private String cardName;
}
