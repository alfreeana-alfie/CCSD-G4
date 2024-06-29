/*
    Filename: ProductsService.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */
package com.ccsd.project.service;

import com.ccsd.project.model.Products;
import com.ccsd.project.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public int createProduct(Products products){
        Products savedProduct = productsRepository.save(products);
        return savedProduct.getId();
    }

    public void updateProduct(int id, Products products){
        if (productsRepository.existsById(id)) {
            products.setId(id);
            productsRepository.save(products);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    public void deleteProduct(int id){
        if (productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    public List<Products> getAllProducts(){
        return (List<Products>) productsRepository.findAll();
    }

    public List<Products> getProductByUserId(int userId){
        return productsRepository.findByUserId(userId);
    }

    public List<Products> getProductById(int id){
        Optional<Products> todoOptional = productsRepository.findById(id);
        if (todoOptional.isPresent()) {
            return Collections.singletonList(todoOptional.get());
        } else {
            throw new RuntimeException("Not found id: " + id);
        }
    }

    public List<Products> getProductByCategoryId(int categoryId){
        return productsRepository.findByCategoryId(categoryId);
    }

    public List<Products> getProductByLanguage(String language){
        return productsRepository.findByLanguage(language);
    }

    public List<Products> getProductByPrice(BigDecimal minimum, BigDecimal maximum) {
        if (minimum != null && maximum != null) {
            return productsRepository.findByPriceBetween(minimum, maximum);
        } else if (minimum != null) {
            return productsRepository.findByPriceGreaterThanEqual(minimum);
        } else if (maximum != null) {
            return productsRepository.findByPriceLessThanEqual(maximum);
        } else {
            return (List<Products>) productsRepository.findAll();
        }
    }

    public List<Products> getProductByKeyword(String keyword){
        return productsRepository.findByKeyword(keyword);
    }

    public List<Products> getProductBySearch(String keyword, int categoryId, String language, BigDecimal minPrice, BigDecimal maxPrice) {
        List<Products> products = (List<Products>) productsRepository.findAll();
        if (keyword != null && !keyword.isEmpty()) {
            products = filterByKeyword(products, keyword);
        }

        if (categoryId > 0) {
            products = filterByCategoryId(products, categoryId);
        }

        if (language != null && !language.isEmpty()) {
            products = filterByLanguage(products, language);
        }

        if (minPrice != null && maxPrice != null) {
            products = filterByPriceRange(products, minPrice, maxPrice);
        } else if (minPrice != null) {
            products = filterByMinPrice(products, minPrice);
        } else if (maxPrice != null) {
            products = filterByMaxPrice(products, maxPrice);
        }

        return products;
    }

    // Helper methods for filtering
    private List<Products> filterByKeyword(List<Products> products, String keyword) {
        return products.stream()
                .filter(product -> product.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || product.getAuthor().toLowerCase().contains(keyword.toLowerCase())
                        || product.getIsbn().toLowerCase().contains(keyword.toLowerCase())
                        || product.getDescription().toLowerCase().contains(keyword.toLowerCase())
                        || product.getPublisher().toLowerCase().contains(keyword.toLowerCase())
                        || product.getLanguage().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Products> filterByCategoryId(List<Products> products, int categoryId) {
        return products.stream()
                .filter(product -> product.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    private List<Products> filterByLanguage(List<Products> products, String language) {
        return products.stream()
                .filter(product -> product.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }

    private List<Products> filterByPriceRange(List<Products> products, BigDecimal minPrice, BigDecimal maxPrice) {
        return products.stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }

    private List<Products> filterByMinPrice(List<Products> products, BigDecimal minPrice) {
        return products.stream()
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .collect(Collectors.toList());
    }

    private List<Products> filterByMaxPrice(List<Products> products, BigDecimal maxPrice) {
        return products.stream()
                .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }
}
