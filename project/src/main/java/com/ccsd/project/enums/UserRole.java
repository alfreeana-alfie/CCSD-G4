/*
    Filename: UserRole.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constant for User Role.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRole {
    @JsonProperty("CUSTOMER") CUSTOMER("CUSTOMER"),
    @JsonProperty("ADMIN") ADMIN("ADMIN");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    UserRole(String name) {
        this.name = name;
    }

    /*
     * Description:
     *   To get Display name for each enums variables.
     * */
    public String getDisplayValue() {
        return this.name;
    }
}
