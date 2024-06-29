/*
    Filename: AuthController.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Controller to handle user authentication flows.
*/

package com.ccsd.project.controllers;

import com.ccsd.project.enums.UserRole;
import com.ccsd.project.model.CustomUserDetails;
import com.ccsd.project.model.Users;
import com.ccsd.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {
    private String rootPath = "/pages/auth/";
    private UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    /*
     * Description:
     *   Redirect to Home (/index).
     * */
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    /*
     * Description:
     *   Redirect to Home with User logged info. (/indexL)
     * */
    @GetMapping("/indexL")
    public String gotoHomeWithUserLogged(Principal principal, Authentication auth, Model model){
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        return rootPath + "index_logged";
    }

    /*
     * Description:
     *   Redirect to Login Form. This form is provided by HttpSecurity package.
     * */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /*
     * Description:
     *   Redirect to Home Page when logout. This form is provided by HttpSecurity package.
     * */
    @GetMapping("/logout")
    public String logout(){
        return "index";
    }

    /*
     * Description:
     *   Redirect to Register Form Page. This form is custom-made with custom user details.
     * */
    @GetMapping("/register")
    public String gotoRegisterForm(Model model){
        model.addAttribute("user", new Users());
        return rootPath + "/register";
    }

    /*
     * Description:
     *   Redirect to View User Profile Page with user session is passed to the page.
     * */
    @GetMapping("/UP/{userId}")
    public String gotoViewUserProfilePage(@PathVariable(value = "userId") String userId, Principal principal, Authentication auth, Model model) {
        Users usersDetails = this.userService.getUserDetails(Integer.parseInt(userId));
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("userDetails", usersDetails);
        return rootPath + "/userProfile";
    }

    /*
     * Description:
     *   To process user data from User form that is retrieve from register.html.
     *   Save the user data to Database > Table (users).
     *   The attribute for Users model is set to "users" which will be used in register.html.
     *   After done register, redirects to /index.
     * */
    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("user") Users users) {
        users.setRole(UserRole.CUSTOMER);
        this.userService.createUsers(users);
        return "redirect:/index";
    }

    /*
     * Description:
     *   To process user data from User form that is retrieve from updateProfile.html.
     *   Save the user data to Database > Table (users).
     *   The attribute for Users model is set to "userDetails" which will be used in updateProfile.html.
     *   After done updating, redirects to /indexL.
     * */
    @PostMapping("/saveProfile")
    public String saveProfile(@ModelAttribute("userDetails") Users users) {
        this.userService.saveProfile(users);
        return "redirect:/indexL";
    }
}
