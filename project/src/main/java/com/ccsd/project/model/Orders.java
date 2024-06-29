/*
    Filename: Orders.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Orders Model
 */
package com.ccsd.project.model;

import com.ccsd.project.enums.OrderStatus;
import com.ccsd.project.enums.PaymentMethod;
import com.ccsd.project.enums.PaymentStatus;
import com.ccsd.project.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;

    @Column(name = "tracking_no")
    private String trackingNo;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name="payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_method")
    private ShippingMethod shippingMethod;
    private String remarks;

    @Column(name = "tax_amt", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal taxAmt;

    @Column(name = "shipping_amt", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal shippingAmt;

    @Column(name="discount_amt", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal discountAmt;

    @Column(name = "total_amt", columnDefinition = "DECIMAL(5,2)")
    private BigDecimal totalAmt;
}
