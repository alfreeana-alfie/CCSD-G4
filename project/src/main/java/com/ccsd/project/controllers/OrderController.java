/*
    Filename: OrderController.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: Controller to handle Order Modules flows.
*/

package com.ccsd.project.controllers;

import com.ccsd.project.enums.OrderStatus;
import com.ccsd.project.enums.PaymentStatus;
import com.ccsd.project.model.CustomUserDetails;
import com.ccsd.project.service.OrdersService;
import com.ccsd.project.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class OrderController {
    private String rootPath = "/pages/orders/";
    private String redirectUrl = "redirect:/OL";
    private OrdersService ordersService;

    @Autowired
    public OrderController(OrdersService ordersService){
        this.ordersService = ordersService;
    }

    /*
     * Description:
     *   Redirect to Order List Page.
     * */
    @GetMapping("/OL")
    public String viewOrderPage(Model model, Principal principal, Authentication auth) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("ordersList", this.ordersService.getAllOrders());
        return rootPath + "orderList";
    }

    /*
     * Description:
     *   Redirect to Customer Order List Page.
     * */
    @GetMapping("/OLC/{userId}")
    public String viewCustomerOrderPage(@PathVariable(value = "userId") int userId, Principal principal, Authentication auth, Model model) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("ordersList", this.ordersService.getOrdersByUserId(userId));
        return rootPath + "orderListCustomer";
    }

    /*
     * Description:
     *   Redirect to View Customer Order Details Page.
     * */
    @GetMapping("/OVC/{id}")
    public String gotoCustomerOrderDetailPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model) {
        Orders orders = this.ordersService.getOrdersById(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("orders", orders);
        return rootPath + "viewOrderCustomer";

    }

    /*
     * Description:
     *   Redirect to Add new Order Page.
     * */
    @GetMapping("/OA")
    public String gotoAddOrderPage(Principal principal, Authentication auth, Model model) {
        Orders orders = new Orders();
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("order", orders);
        return rootPath + "viewOrderCustomer";
    }

    /*
     * Description:
     *   Redirect to Update Order Form Page.
     * */
    @GetMapping("/OU/{id}")
    public String gotoUpdateOrderPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model) {
        Orders orders = this.ordersService.getOrdersById(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("order", orders);
        return rootPath + "updateOrders";
    }

    /*
     * Description:
     *   Redirect to View Order Details Page.
     * */
    @GetMapping("/OV/{id}")
    public String gotoOrderDetailPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model) {
        Orders orders = this.ordersService.getOrdersById(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("orders", orders);
        return rootPath + "viewOrder";

    }



    /*
     * Description:
     *   Searching Order by Tracking No
     * */
    @PostMapping("/search")
    public String searchOrders(@Param("trackingNo") String trackingNo, Model model, Principal principal, Authentication auth) {
        List<Orders> orders1 = this.ordersService.searchByTrackingNo(trackingNo);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("trackingNo", trackingNo);
        model.addAttribute("ordersList", orders1);
        return rootPath + "orderList";
    }

    /*
     * Description:
     *   Searching Order by Tracking No
     * */
    @PostMapping("/searchCustomerOrder")
    public String searchCustomerOrder(@Param("trackingNo") String trackingNo, Model model, Principal principal, Authentication auth) {
        List<Orders> orders1 = this.ordersService.searchByTrackingNo(trackingNo);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("trackingNo", trackingNo);
        model.addAttribute("ordersList", orders1);
        return "redirect:/OLC/" + users.getUsers().getId();
    }

    /*
     * Description:
     *   Saving and redirect to Order List page
     * */
    @PostMapping("/save")
    public String saveOrders(@ModelAttribute("order") Orders orders) {
        System.out.println("order" + orders.getBillingAddress());
        this.ordersService.save(orders);
        return redirectUrl;
    }

    @PostMapping("/saveOrderCust")
    public String saveOrdersCustomer(@ModelAttribute("order") Orders orders, Model model, Principal principal, Authentication auth) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        System.out.println("order" + orders.getId());
        this.ordersService.save(orders);
        return "redirect:/OLC/" + users.getUsers().getId();
    }

    /*
     * Description:
     *   Deleting Orders by ID
     * */
    @GetMapping("/OD/{id}")
    public String deleteOrderById(@PathVariable(value = "id") int id) {
        this.ordersService.deleteOrders(id);
        return redirectUrl;

    }

    /*
     * Description:
     *   Updating Payment and Order Status and redirect to Order List page
     * */
    @PostMapping("/updatePaymentStatus")
    public String updatePaymentStatus(@ModelAttribute("order") Orders orders, PaymentStatus paymentStatus, OrderStatus orderStatus) {
        this.ordersService.updatePaymentStatus(orders.getId(), paymentStatus);
        this.ordersService.updateOrderStatus(orders.getId(), orderStatus);
        return redirectUrl;

    }
}