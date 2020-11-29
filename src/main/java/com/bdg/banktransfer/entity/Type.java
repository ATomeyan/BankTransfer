package com.bdg.banktransfer.entity;

public enum Type {
    Deposit("Deposit"),
    Withdrawal("Withdrawal");
    String type;

    Type(String type) {
        this.type = type;
    }
}