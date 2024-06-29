/*
    Filename: CustomUserDetailsService.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Services to handle Custom User Details Modules flows.
*/

package com.ccsd.project.service;

import com.ccsd.project.model.CustomUserDetails;
import com.ccsd.project.model.Users;
import com.ccsd.project.repository.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@Setter
@Getter
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    /*
     * Description:
     *   Method: Load user details WHERE username=?.
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = this.usersRepository.findByUsername(username);
        return new CustomUserDetails(users);
    }
}
