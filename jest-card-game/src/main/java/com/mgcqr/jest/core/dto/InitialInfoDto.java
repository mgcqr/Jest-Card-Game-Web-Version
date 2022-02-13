package com.mgcqr.jest.core.dto;

import com.mgcqr.jest.core.stuff.MailBox;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InitialInfoDto extends MailBoxDto {
    //id in user database
    //used in core as userName
    //table.noms[]
    private List<String> userIds;
}
