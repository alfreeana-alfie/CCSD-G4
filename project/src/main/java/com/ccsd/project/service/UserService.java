/*
    Filename: UserService.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Services to handle User (Customer) Modules flows.
*/

package com.ccsd.project.service;

import com.ccsd.project.model.Users;
import com.ccsd.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository userRepository, PasswordEncoder passwordEncoder){
        this.usersRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Encoding password during registration.
    }

    /*
     * Description:
     *   Method: Get User Details where id=?  [ADMIN].
     * */
    public Users getUserDetails(int id) {
        if (this.usersRepository.existsById(id) && this.usersRepository.findById(id).isPresent()) { // Checking if user exists or not
            return this.usersRepository.findById(id).get();
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Get all Users [ADMIN].
     * */
    public List<Users> getAllUsers() {
        return (List<Users>) this.usersRepository.findAll();
    }

    /*
     * Description:
     *   Method: Get users WHERE name=? [ADMIN].
     * */
    public List<Users> findByName(String name) {
        return this.usersRepository.findByName(name);
    }

    /*
     * Description:
     *   Method: Create new user.
     * */
    public Users createUsers(Users users) {
        users.setPassword(this.passwordEncoder.encode(users.getPassword()));
        return this.usersRepository.save(users);
    }

    /*
     * Description:
     *   Method: Save user Profile.
     * */
    public Users saveProfile(Users users) {
        users.setPassword(users.getPassword());
        return this.usersRepository.save(users);
    }

    /*
     * Description:
     *   Method: Updating User Information [ADMIN].
     * */
    public Users updateUserDetails(int id, Users users) {
        if (this.usersRepository.existsById(id)) { // Checking if user exists or not
            this.usersRepository.save(users);
            return users;
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Method: Deleting user WHERE id=? [ADMIN].
     * */
    public void deleteUser(int id) {
        if(this.usersRepository.existsById(id)) { // Checking if user exists or not
            this.usersRepository.deleteById(id);
        }else {
            throw new RuntimeException("ID NOT FOUND: " + id);
        }
    }
 }
