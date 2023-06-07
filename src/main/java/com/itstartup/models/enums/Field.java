package com.itstartup.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Field {
    BUSINESS("Бизнес"),
    AUTOMATION("Автоматизация"),
    REALTY("Недвижимость"),
    EDUCATION("Образование"),
    PUBLIC_SECTOR("Госсектор");
    private final String name;

}
