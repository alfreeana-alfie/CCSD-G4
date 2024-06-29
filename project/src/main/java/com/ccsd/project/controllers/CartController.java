/*
    Filename: CartController.java
    File Updated: 2024-06-24
    Edited by: Arief Sayyidi
    Description: Controller to handle cart Modules flows.
*/

package com.ccsd.project.controllers;

import com.ccsd.project.enums.OrderStatus;
import com.ccsd.project.enums.PaymentStatus;
import com.ccsd.project.model.Carts;
import com.ccsd.project.model.CustomUserDetails;
import com.ccsd.project.model.Orders;
import com.ccsd.project.model.Products;
import com.ccsd.project.service.CartService;
import com.ccsd.project.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Random;


@Controller
public class CartController {
    private String rootPath = "/pages/carts/";
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    /*
     * Description:
     *   Redirect to Cart List Page (/CL).
     * */
    @GetMapping("/CL")
    public String gotoCartListPage(Model model, Principal principal, Authentication auth) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("cartList", this.cartService.getAllList());
        return rootPath + "cartList";
    }

    /*
     * Description:
     *   Redirect to Add new cart page(/CA).
     * */
    @GetMapping("/CA")
    public String gotoAddCartPage(Model model, Principal principal, Authentication auth) {
        Carts carts = new Carts();
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("cart", carts);
        return rootPath + "addCarts";
    }

    /*
     * Description:
     *   Redirect to Update cart Page (/CU/{id}).
     * */
    @GetMapping("/CU/{id}")
    public String gotoUpdateCartPage(@PathVariable(value = "id") int id, Model model, Principal principal, Authentication auth) {
        Carts carts = this.cartService.getCartById(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("order", carts);
        return rootPath + "updateCarts";
    }

    /*
     * Description:
     *   Redirect to View Cart View by User ID Page (/CV/{userId}).
     * */
    @GetMapping("/CustomerCart/{userId}")
    public String gotoCartDetailPage(@PathVariable(value = "userId") int userId, Model model, Principal principal, Authentication auth) {
        List<Carts> products = this.cartService.getCartsByUserId(userId);

        double sum=0.00;
        for(Carts cart: products) {
            sum += cart.getTotalAmt();
        }

        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("products", products);
        model.addAttribute("sum", sum);
        return rootPath + "viewCart";
    }

    /*
     * Description:
     *   Redirect to View Cart View by User ID Page (/CV/{userId}).
     * */
    @GetMapping("/checkOut/{userId}")
    public String gotoCheckOutPage(@ModelAttribute("order") Orders orders, @PathVariable(value = "userId") int userId, Model model,
                                   Principal principal, Authentication auth) {
        List<Carts> products = this.cartService.getCartsByUserId(userId);
        StringBuilder userAddress = new StringBuilder();
        double cartAmt =0.00;
        double taxAmt = 2.0;
        double shippingAmt = 5.0;
        double grandTotal;

        for(Carts cart: products) {
            cartAmt += cart.getTotalAmt();
        }

        grandTotal = cartAmt + taxAmt + shippingAmt;

        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();

         userAddress.append(users.getUsers().getAddress1()).append(", ")
                .append(users.getUsers().getAddress2())
                .append(", ")
                .append(users.getUsers().getAddress3())
                .append(" ")
                .append(users.getUsers().getPostcode())
                .append(" ")
                .append(users.getUsers().getCity())
                .append(", ")
                .append(users.getUsers().getState());

        model.addAttribute("users", users.getUsers());
        model.addAttribute("billingAddress", userAddress.toString());
        model.addAttribute("shippingAddress", userAddress.toString());
        model.addAttribute("products", products);
        model.addAttribute("cartAmt", cartAmt);
        model.addAttribute("discountAmt", 0);
        model.addAttribute("taxAmt", taxAmt);
        model.addAttribute("shippingAmt", shippingAmt);
        model.addAttribute("totalAmt", grandTotal);
        model.addAttribute("orderStatus", OrderStatus.PENDING);
        model.addAttribute("paymentStatus", PaymentStatus.PROCESSING);
        model.addAttribute("trackingNo", generatedString());
        return rootPath + "viewCheckout";
    }

    /*
     * Description:
     *   Saving and Redirect to View Cart List Page (/CL).
     * */
    @PostMapping("/saveCart")
    public String saveCarts(@ModelAttribute("product") Carts carts) {
        double totalAmt = carts.getQuantity()*carts.getUnitPrice();
        carts.setProductName(carts.getProductName());
        carts.setTotalAmt(totalAmt);
        this.cartService.save(carts);
        return "redirect:/PBL";
    }

    /*
     * Description:
     *   Deleting Cart by ID.
     * */
    @GetMapping("/CD/{id}")
    public String deleteCartById(@PathVariable(value = "id") int id, Model model, Principal principal, Authentication auth) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        this.cartService.delete(id);
        return "redirect:/CustomerCart/" + users.getUsers().getId();

    }

    public String generatedString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
