package com.mgcqr.jest.model;

import com.mgcqr.jest.dto.GameInstructionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstructionInfo extends GameInstructionDto {
    private String userId;
}
