package com.mgcqr.jest.core.dto;

import com.mgcqr.jest.core.stuff.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TrophyDisplayDto extends MailBoxDto {
    private Card[] trophy;
}
