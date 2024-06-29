/*
    Filename: ShippingMethod.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constant for Shipping Method.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShippingMethod {
    @JsonProperty("Standard Delivery") STANDARD("Standard Delivery"),
    @JsonProperty("Express Delivery") EXPRESS("Express Delivery");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    ShippingMethod(String name) {
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
