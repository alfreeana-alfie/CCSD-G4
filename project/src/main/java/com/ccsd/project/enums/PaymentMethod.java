/*
    Filename: PaymentMethod.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constant for Payment Method.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethod {
    @JsonProperty("Credit Card") CC("Credit Card"),
    @JsonProperty("Debit Card") DB("Debit Card"),
    @JsonProperty("Cash on Delivery") COD("Cash on Delivery"),
    @JsonProperty("Online Banking") OB("Online Banking");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    PaymentMethod(String name) {
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
