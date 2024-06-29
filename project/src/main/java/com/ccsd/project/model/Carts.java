/*
    Filename: Carts.java
    File Updated: 2024-06-24
    Edited by: Arief Sayyidi
    Description: Cart Model
 */
package com.ccsd.project.model;

import com.ccsd.project.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Carts")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    private int quantity;

    @Column(name = "unit_price", columnDefinition = "DECIMAL(5,2)")
    private double unitPrice;

    @Column(name = "total_amt", columnDefinition = "DECIMAL(5,2)")
    private double totalAmt;

    private Status status;

    public double getTotal() {
        return quantity*unitPrice;
    }
}
