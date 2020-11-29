package com.bdg.banktransfer.entity;

public enum RoleName {
    USER("USER"),
    ADMIN("ADMIN");
    String role;

    RoleName(String role) {
        this.role = role;
    }
}