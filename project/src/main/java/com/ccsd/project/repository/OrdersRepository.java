/*
    Filename: OrdersRepository.java
    File Updated: 2024-06-24
    Edited by: Nur Alfreeana
    Description: Repository for Orders
 */
package com.ccsd.project.repository;

import com.ccsd.project.model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {

    /*
     * Description:
     *   Find Orders WHERE userId = ?
     * */
    List<Orders> findByUserId(int userId);

    /*
     * Description:
     *   Find Orders WHERE Tracking No = ?
     * */
    List<Orders> findByTrackingNo(String trackingNo);
}
