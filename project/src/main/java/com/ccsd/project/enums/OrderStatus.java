/*
    Filename: OrderStatus.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: To store ENUMS Constanst for Order Status.
 */
package com.ccsd.project.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("PENDING") PENDING("Pending"),
    @JsonProperty("CANCELLED") CANCELLED("Cancelled"),
    @JsonProperty("SHIPPED") SHIPPED("Shipped"),
    @JsonProperty("RECEIVED") RECEIVED("Received");

    private String name;

    /*
     * Description:
     *   Constructor
     * */
    OrderStatus(String name) {
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
