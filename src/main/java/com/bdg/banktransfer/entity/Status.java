package com.bdg.banktransfer.entity;

public enum Status {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    CANCEL("CANCEL");

    String status;

    Status(String status) {
        this.status = status;
    }
}