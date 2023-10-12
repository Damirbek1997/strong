package com.gym.strong.models;

import lombok.Data;

import java.util.List;

@Data
public class Year {
    private Long name;
    private List<Month> months;
}
