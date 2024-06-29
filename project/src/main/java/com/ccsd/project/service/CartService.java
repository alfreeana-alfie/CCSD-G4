/*
    Filename: CartController.java
    File Updated: 2024-06-24
    Edited by: Arief Sayyidi
    Description: Controller to handle cart Modules flows.
*/

package com.ccsd.project.service;

import com.ccsd.project.model.Carts;
import com.ccsd.project.repository.CartRepository;
import com.ccsd.project.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /*
     * Description:
     *   Saving new cart value.
     * */
    public void save(Carts carts) {
        this.cartRepository.save(carts);
    }

    /*
     * Description:
     *   Get all Cart List.
     * */
    public List<Carts> getAllList() {
        return (List<Carts>) this.cartRepository.findAll();
    }

    /*
     * Description:
     *   Get cart by ID.
     * */
    public Carts getCartById(int id) {
        if (this.cartRepository.existsById(id) && this.cartRepository.findById(id).isPresent()) {
            return this.cartRepository.findById(id).get();
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    /*
     * Description:
     *   Get cart by userId.
     * */
    public List<Carts> getCartsByUserId(int userId) {
        if (this.cartRepository.existsByUserId(userId)) {
            return this.cartRepository.getCartsByUserId(userId);
        } else {
            throw new RuntimeException("Not found id: " + userId);
        }

//        try{
//            return this.cartRepository.getCartsByUserId(userId);
//        }catch(RuntimeException e) {
//            throw new RuntimeException("Not found id: " + userId);
//        }
    }

    /*
     * Description:
     *   Deleting cart values.
     * */
    public void delete(int id) {
        if(this.cartRepository.existsById(id)){
            this.cartRepository.deleteById(id);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }
}
