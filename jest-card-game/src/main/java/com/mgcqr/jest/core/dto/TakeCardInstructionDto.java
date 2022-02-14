package com.mgcqr.jest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TakeCardInstructionDto extends MailBoxAbsDto {
    //design extended from game core design
    //index of array (table.joueurs[])
    private Integer playerID;
    private String cardName;
}
