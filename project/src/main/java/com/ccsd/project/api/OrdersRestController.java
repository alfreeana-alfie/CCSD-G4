/*
    Filename: OrderController.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
 */

package com.ccsd.project.api;

import com.ccsd.project.model.Orders;
import com.ccsd.project.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {
    private OrdersService ordersService;

    @Autowired
    public OrdersRestController(OrdersService ordersService){
        this.ordersService = ordersService;
    }

    // Description: Create new Orders
    @PostMapping()
    public ResponseEntity<List<Orders>> createOrders(@RequestBody Orders orders){
        this.ordersService.createOrders(orders);
        List<Orders> ordersList = this.ordersService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }

    // Description: Get all Order List
    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> ordersList = this.ordersService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }

    // Description: Get order by users ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getOrderByUserId(@PathVariable int userId) {
        List<Orders> ordersList = this.ordersService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ordersList);
    }

    // Description: Get Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable int id) {
        Orders ordersList = this.ordersService.getOrdersById(id);
        return ResponseEntity.ok(ordersList);
    }

    // Description: Update Orders
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable int id, @RequestBody Orders orders) {
        this.ordersService.updateOrders(id, orders);
        Orders ordersList = this.ordersService.getOrdersById(id);
        return ResponseEntity.ok(ordersList);
    }

    // Description: Patch Orders Status
    @PatchMapping("/statusOrder/{id}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable int id, @RequestBody Orders status){
        this.ordersService.updateOrderStatus(id, status.getOrderStatus());
        Orders ordersList = this.ordersService.getOrdersById(id);
        return ResponseEntity.ok(ordersList);
    }

    // Description: Patch Payment Status
    @PatchMapping("/statusPayment/{id}")
    public ResponseEntity<Orders> updatePaymentStatus(@PathVariable int id, @RequestBody Orders status){
        this.ordersService.updatePaymentStatus(id, status.getPaymentStatus());
        Orders ordersList = this.ordersService.getOrdersById(id);
        return ResponseEntity.ok(ordersList);
    }

    // Description: Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Orders>> deleteOrders(@PathVariable int id) {
        this.ordersService.deleteOrders(id);
        List<Orders> ordersList = this.ordersService.getAllOrders();
        return ResponseEntity.ok(ordersList);
    }
}
