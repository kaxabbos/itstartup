package com.itstartup.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    USER("Инвестор"),
    ADMIN("Управляющий"),
    STARTUP("Стартапер");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
