package com.mgcqr.jest.core.dto;

import com.mgcqr.jest.core.stuff.Card;
import lombok.Data;

@Data
public class UserGameResult {
    private String userId;
    private Card[] jest;
    private int score;
}
