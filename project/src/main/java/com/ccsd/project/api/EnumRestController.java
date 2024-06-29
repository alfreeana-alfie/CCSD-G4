/*
    Filename: EnumRestController.java
    File Updated: 2024-06-25
    Edited by: Nur Alfreeana
 */
package com.ccsd.project.api;

import com.ccsd.project.enums.OrderStatus;
import com.ccsd.project.enums.PaymentMethod;
import com.ccsd.project.enums.PaymentStatus;
import com.ccsd.project.enums.ShippingMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enums")
public class EnumRestController {

    @GetMapping("/orderStatus")
    public List<OrderStatus> getOrderStatus(){
        return Arrays.asList(OrderStatus.values());
    }

    @GetMapping("/paymentStatus")
    public List<PaymentStatus> getPaymentStatus(){
        return Arrays.asList(PaymentStatus.values());
    }

    @GetMapping("/paymentMethod")
    public List<PaymentMethod> getPaymentMethod(){
        return Arrays.asList(PaymentMethod.values());
    }

    @GetMapping("/shippingMethod")
    public List<ShippingMethod> getShippingMethod(){
        return Arrays.asList(ShippingMethod.values());
    }
}
