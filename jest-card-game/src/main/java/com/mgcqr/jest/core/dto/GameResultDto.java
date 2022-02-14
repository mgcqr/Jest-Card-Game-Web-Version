package com.mgcqr.jest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class GameResultDto extends MailBoxAbsDto {
    private List<UserGameResult> results;
}
