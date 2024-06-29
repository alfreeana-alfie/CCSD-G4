/*
    Filename: PaymentStatus.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constant for Payment Status.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentStatus {
    @JsonProperty("PROCESSING") PROCESSING("Processing"),
    @JsonProperty("REFUND") REFUND("Refund"),
    @JsonProperty("SUCCESSFUL") SUCCESSFUL("Successful"),
    @JsonProperty("FAILED") FAILED("Failed");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    PaymentStatus(String name) {
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
