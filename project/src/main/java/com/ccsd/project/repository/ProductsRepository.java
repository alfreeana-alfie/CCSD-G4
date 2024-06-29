/*
    Filename: ProductsRepository.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */
package com.ccsd.project.repository;

import com.ccsd.project.model.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Integer> {

    List<Products> findByUserId(int UserId);
    List<Products> findByCategoryId(int CategoryId);
    List<Products> findByLanguage(String language);

    @Query("SELECT p FROM Products p WHERE " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.isbn) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.publisher) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.language) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> findByKeyword(@Param("keyword") String keyword);

    List<Products> findByPriceBetween(BigDecimal minimum, BigDecimal maximum);
    List<Products> findByPriceGreaterThanEqual(BigDecimal minimum);
    List<Products> findByPriceLessThanEqual(BigDecimal maximum);
}
