/*
    Filename: ProductsRestController.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */

package com.ccsd.project.api;

import com.ccsd.project.model.Products;
import com.ccsd.project.service.FileStorageService;
import com.ccsd.project.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsRestController {

    private ProductsService productsService;
    private FileStorageService fileStorageService;


    @Autowired
    public ProductsRestController(ProductsService productsService, FileStorageService fileStorageService){

        this.productsService = productsService;
        this.fileStorageService = fileStorageService;
    }

//
//    @PostMapping()
//    public ResponseEntity<List<Products>> createProduct(@RequestBody Products products) {
//        productsService.createProduct(products);
//        List<Products> updatedProducts = productsService.getAllProducts();
//        return ResponseEntity.ok(updatedProducts);
//    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<List<Products>> createProduct(
            @RequestPart("product") Products products,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        if (file != null) {
            String filePath = fileStorageService.storeFile(file);
            products.setImageUrl(filePath);
        }
        productsService.createProduct(products);
        List<Products> updatedProducts = productsService.getAllProducts();
        return ResponseEntity.ok(updatedProducts);
    }

    @GetMapping()
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> todos = productsService.getAllProducts();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Products>> getProductById(@PathVariable int id) {
        List<Products> products = productsService.getProductById(id);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Products>> updateProduct(@PathVariable int id, @RequestBody Products products) {
        productsService.updateProduct(id, products);
        List<Products> updatedProducts = productsService.getProductById(id);
        return ResponseEntity.ok(updatedProducts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Products>> deleteProduct(@PathVariable int id) {
        productsService.deleteProduct(id);
        List<Products> updatedProducts = productsService.getAllProducts();
        return ResponseEntity.ok(updatedProducts);
    }
}
