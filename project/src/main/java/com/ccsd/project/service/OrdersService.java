/*
    Filename: OrdersService.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: Services to handle Orders Modules flows.
 */
package com.ccsd.project.service;

import com.ccsd.project.model.Orders;
import com.ccsd.project.repository.OrdersRepository;
import com.ccsd.project.enums.OrderStatus;
import com.ccsd.project.enums.PaymentStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    public OrdersRepository ordersRepository;

    @Autowired
    public OrdersService (OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    /*
     * Description:
     *   Method: Get all Orders List [ADMIN].
     * */
    public List<Orders> getAllOrders() {
        return (List<Orders>) this.ordersRepository.findAll();
    }

    /*
     * Description:
     *   Method: Get Order where user_id=?  [CUSTOMER].
     * */
    public List<Orders> getOrdersByUserId(int userId) {
        return this.ordersRepository.findByUserId(userId);
    }

    /*
     * Description:
     *   Method: Get Order List Details where trackingNo=?  [ADMIN].
     * */
    public List<Orders> searchByTrackingNo(String trackingNo) {
        return this.ordersRepository.findByTrackingNo(trackingNo);
    }

    /*
     * Description:
     *   Method: Get Order Details where id=?  [ADMIN].
     * */
    public Orders getOrdersById(int id) {
        if (this.ordersRepository.existsById(id)) {
            Orders todoOptional = this.ordersRepository.findById(id).get();
            return todoOptional;
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Create new Orders.
     * */
    public Orders createOrders(Orders orders) {
        return this.ordersRepository.save(orders);
    }

    /*
     * Description:
     *   Method: Create/Save new Orders.
     * */
    public void save(Orders orders) {
        this.ordersRepository.save(orders);
    }

    /*
     * Description:
     *   Method: Updating Order Details WHERE id=?
     * */
    public Orders updateOrders(int id, Orders orders) {
        if (this.ordersRepository.existsById(id)) { // Checking if user exists or not
            Orders ordersDetails = this.ordersRepository.findById(id).get();
            ordersDetails.setUserId(orders.getUserId());
            ordersDetails.setTrackingNo(orders.getTrackingNo());
            ordersDetails.setBillingAddress(orders.getBillingAddress());
            ordersDetails.setShippingAddress(orders.getShippingAddress());
            ordersDetails.setOrderStatus(orders.getOrderStatus());
            ordersDetails.setPaymentStatus(orders.getPaymentStatus());
            ordersDetails.setPaymentMethod(orders.getPaymentMethod());
            ordersDetails.setShippingMethod(orders.getShippingMethod());
            ordersDetails.setRemarks(orders.getRemarks());
            ordersDetails.setTaxAmt(orders.getTaxAmt());
            ordersDetails.setShippingAmt(orders.getShippingAmt());
            ordersDetails.setDiscountAmt(orders.getDiscountAmt());
            ordersDetails.setTotalAmt(orders.getTotalAmt());
            this.ordersRepository.save(ordersDetails);
            return ordersDetails;
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Updating Order Status WHERE id=?
     * */
    public void updateOrderStatus(int id, OrderStatus status) {
        if (this.ordersRepository.existsById(id)) { // Checking if user exists or not
            Orders orders = this.ordersRepository.findById(id).get();
            orders.setOrderStatus(status);
            this.ordersRepository.save(orders);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Updating Payment Status WHERE id=?
     * */
    public void updatePaymentStatus(int id, PaymentStatus status) {
        if (this.ordersRepository.existsById(id)) { // Checking if user exists or not
            Orders orders = this.ordersRepository.findById(id).get();
            orders.setPaymentStatus(status);
            this.ordersRepository.save(orders);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Deleting order WHERE id=?
     * */
    public void deleteOrders(int id) {
        if(this.ordersRepository.existsById(id)){ // Checking if user exists or not
            this.ordersRepository.deleteById(id);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }
}
