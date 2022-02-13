package com.mgcqr.jest.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeCardDto extends MailBoxDto{
    //design extended from game core design
    //index of array (table.joueurs[])
    private Integer playerID;
    private Boolean isFaceUp;
}
