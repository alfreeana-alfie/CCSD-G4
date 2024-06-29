package com.ccsd.project.api;

import com.ccsd.project.model.Users;
import com.ccsd.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {
    private UserService userService;

    @Autowired
    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<Users> getUserList() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/register")
    public Users createUser(@RequestBody Users users) {
        return this.userService.createUsers(users);
    }

    // Description: Update Orders
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUserDetails(@PathVariable int id, @RequestBody Users users) {
        this.userService.updateUserDetails(id, users);
        Users userDetails = this.userService.getUserDetails(id);
        return ResponseEntity.ok(userDetails);
    }

    // Description: Update Orders
    @PatchMapping("/{id}")
    public ResponseEntity<Users> patchUserDetail(@PathVariable int id, @RequestBody Users users) {
        this.userService.updateUserDetails(id, users);
        Users userDetails = this.userService.getUserDetails(id);
        return ResponseEntity.ok(userDetails);
    }

    // Description: Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Users>> Users(@PathVariable int id) {
        this.userService.deleteUser(id);
        List<Users> usersList = this.userService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }
}
