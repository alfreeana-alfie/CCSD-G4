/*
    Filename: CartRepository.java
    File Updated: 2024-06-24
    Edited by: Arief Sayyidi
    Description: Repository for Carts
 */

package com.ccsd.project.repository;

import com.ccsd.project.model.Carts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Carts, Integer> {

    /*
     * Description:
     *   Find Cart by User Id = userID
     * */
    List<Carts> findCartByUserId(int userId);

    /*
     * Description:
     *   Checking cart existed based on user ID
     * */
    boolean existsByUserId(int userId);

//    @Query("SELECT p, p1 FROM Carts p join Products p1 on p.productId=p1.id where p.userId=:userId")
    List<Carts> getCartsByUserId(@Param("userId") int userId);
}
