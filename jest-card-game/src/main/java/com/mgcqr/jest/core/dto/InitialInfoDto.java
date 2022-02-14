package com.mgcqr.jest.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InitialInfoDto extends MailBoxAbsDto {
    //id in user database
    //used in core as userName
    //table.noms[]
    private List<String> userIds;
}
