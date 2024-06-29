/*
    Filename: UserController.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Controller to handle User (Customer) Modules flows.
*/

package com.ccsd.project.controllers;

import com.ccsd.project.enums.UserRole;
import com.ccsd.project.model.CustomUserDetails;
import com.ccsd.project.model.Orders;
import com.ccsd.project.model.Users;
import com.ccsd.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private String rootPath = "/pages/users/";
    private String redirectUrl = "redirect:/UL";
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /*
     * Description:
     *   Redirect to User List Page.
     * */
    @GetMapping("/UL")
    public String viewOrderPage(Model model, Principal principal, Authentication auth) {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("usersList", this.userService.getAllUsers());
        return rootPath + "userList";
    }

    /*
     * Description:
     *   Redirect to Add new User Page.
     * */
    @GetMapping("/UA")
    public String gotoAddNewUserPage(Principal principal, Authentication auth, Model model) {
        Users user = new Users();
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("user", user);
        return rootPath + "addUser";
    }

    /*
     * Description:
     *   Redirect to Updating User Details Page.
     * */
    @GetMapping("/UU/{id}")
    public String gotoUpdateUserPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model) {
        Users userDetails = this.userService.getUserDetails(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("user", userDetails);
        return rootPath + "updateUser";
    }

    /*
     * Description:
     *   Redirect to User View Page.
     * */
    @GetMapping("/UV/{id}")
    public String gotoUserDetailPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model) {
        Users userDetails = this.userService.getUserDetails(id);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("userDetails", userDetails);
        return rootPath + "viewUser";

    }

    /*
     * Description:
     *   Searching user by their name
     * */
    @PostMapping("/searchU")
    public String searchU(@Param("name") String name, Model model, Principal principal, Authentication auth) {
        List<Users> usersList = this.userService.findByName(name);
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("name", name);
        model.addAttribute("usersList", usersList);
        return rootPath + "userList";
    }

    /*
     * Description:
     *   Saving and redirect to User List page
     * */
    @PostMapping("/saveUser")
    public String saveUsers(@ModelAttribute("user") Users user, UserRole role) {
        user.setRole(role);
        this.userService.createUsers(user);
        return redirectUrl;
    }

    /*
     * Description:
     *   Deleting and redirect to User List page
     * */
    @GetMapping("/UD/{id}")
    public String deleteOrderById(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(id);
        return redirectUrl;

    }
}