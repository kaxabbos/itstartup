package com.itstartup.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Kinds {
    COMMERCIAL("Коммерческий"),
    NON_COMMERCIAL("Некоммерческий");

    private final String name;

}
