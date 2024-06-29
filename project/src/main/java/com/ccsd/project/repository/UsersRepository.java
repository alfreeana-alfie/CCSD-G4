/*
    Filename: UsersRepository.java
    File Updated: 2024-06-24
    Edited by: Iman Hidayat
    Description: Repository for Users
 */
package com.ccsd.project.repository;

import com.ccsd.project.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<Users,Integer> {

    /*
     * Description:
     *   Find Users WHERE username = ?
     * */
    Users findByUsername(String username);

    /*
     * Description:
     *   Find Users WHERE name = ?
     * */
    List<Users> findByName(String name);
}
