/*
    Filename: Status.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constant for Status.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("ACTIVE") ACTIVE("Active"),
    @JsonProperty("INACTIVE") INACTIVE("Inactive");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    Status(String name) {
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
