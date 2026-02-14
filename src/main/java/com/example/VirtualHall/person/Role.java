package com.example.VirtualHall.person;

public enum Role {
    USER,
    ADMIN;

    public String getAuthority() {
        return name();
    }
}
