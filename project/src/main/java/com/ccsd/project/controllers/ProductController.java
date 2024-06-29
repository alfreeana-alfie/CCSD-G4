/*
    Filename: ProductController.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
    Description: Controller to handle product Modules flows.
*/

package com.ccsd.project.controllers;

import com.ccsd.project.model.*;
import com.ccsd.project.service.CategoriesService;
import com.ccsd.project.service.FileStorageService;
import com.ccsd.project.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private String rootPath= "pages/products/";
    private String redirectPath = "redirect:/PSL";

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private FileStorageService fileStorageService;

    /*
     * Description:
     *   Redirect to Add new Product Page.
     * */
    @RequestMapping("/PA")
    public String addProductsPage(Model model, Principal principal, Authentication auth){
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());

        List<Categories> categories = categoriesService.getAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("products", new Products());

        return rootPath + "addProducts"; // name of the Thymeleaf template
    }

    /*
     * Description:
     *   Redirect to Seller (Admin) Product List Page.
     * */
    @RequestMapping("/PSL")
    public String sellerProductsListPage(Model model, Principal principal, Authentication auth){
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());

        List<Products> products = productsService.getProductByUserId(users.getUsers().getId());
        model.addAttribute("products", products);

        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);

        Map<Integer, String> categoryMap = categories.stream()
                .collect(Collectors.toMap(Categories::getId, Categories::getDescription));
        model.addAttribute("categoryMap", categoryMap);

        // Debugging log
        // System.out.println("Category Map: " + categoryMap);
        // products.forEach(product -> System.out.println("Product ID: " + product.getId() + ", Category ID: " + product.getCategoryId()));

        return rootPath + "productsListS"; // name of the Thymeleaf template
    }

    /*
     * Description:
     *   Redirect to Buyer Product List Page.
     * */
    @RequestMapping("/PBL")
    public String buyerProductsListPage(@ModelAttribute("cart") Carts carts, Model model, Principal principal, Authentication auth){
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());

        List<Products> products = productsService.getAllProducts();
        model.addAttribute("products", products);

        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);

        return rootPath + "productsList"; // name of the Thymeleaf template
    }

    /*
     * Description:
     *   Redirect to Search Buyer Product List Page.
     * */
    @RequestMapping("/PBLS")
    public String buyerProductsListPageSearch(@ModelAttribute("cart") Carts carts, Model model,
                                              @RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) Integer categoryId,
                                              @RequestParam(required = false) String language,
                                              @RequestParam(required = false) BigDecimal minPrice,
                                              @RequestParam(required = false) BigDecimal maxPrice,
                                              Principal principal, Authentication auth) {

        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());

        // Fetch products based on filtered criteria
        List<Products> filteredProducts = productsService.getProductBySearch(keyword, categoryId, language, minPrice, maxPrice);
        model.addAttribute("products", filteredProducts);

        // Fetch all categories to populate dropdown or filter options
        List<Categories> categories = categoriesService.getAllCategories();
        model.addAttribute("categories", categories);

        return rootPath + "productsList"; // name of the Thymeleaf template
    }

    /*
     * Description:
     *   Deleting product by ID.
     * */
    @GetMapping("/PD/{id}")
    public String deleteProductById(@PathVariable(value = "id") int id) {
        this.productsService.deleteProduct(id);
        return redirectPath;

    }

    /*
     * Description:
     *   Redirect to update Product Page.
     * */
    @RequestMapping("/PU/{id}")
    public String updateProductsPage(@PathVariable(value = "id") int id, Principal principal, Authentication auth, Model model){
        List<Categories> categories = categoriesService.getAllCategories();
        List<Products> productsList = productsService.getProductById(id);

        Products product = productsList.get(0);

        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();
        model.addAttribute("users", users.getUsers());
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return rootPath + "updateProducts"; // name of the Thymeleaf template
    }

    /*
     * Description:
     *   Saving the new product.
     * */
    @PostMapping(value = "/createProduct", consumes = {"multipart/form-data"})
    public String createProduct(
            @ModelAttribute Products products,
            @RequestPart(value = "file", required = false) MultipartFile file, Principal principal, Authentication auth)
            throws HttpMediaTypeNotSupportedException {
        CustomUserDetails users = (CustomUserDetails) auth.getPrincipal();

        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new HttpMediaTypeNotSupportedException("Only image files are supported.");
            }

            String filePath = fileStorageService.storeFile(file);
            products.setImageUrl(filePath);
        }

        products.setUserId(users.getUsers().getId());
        productsService.createProduct(products);
        return redirectPath;
    }

    /*
     * Description:
     *   Updating product's value.
     * */
    @PostMapping(value = "/updateProduct/{id}", consumes = {"multipart/form-data"})
    public String updateProduct(
            @PathVariable int id,
            @ModelAttribute Products products,
            @RequestPart(value = "file", required = false) MultipartFile file)
            throws HttpMediaTypeNotSupportedException {

        // Fetch the existing product
        List<Products> existingProductsList = productsService.getProductById(id);

        if (existingProductsList == null) {
            throw new IllegalArgumentException("Product not found for id: " + id);
        }

        Products existingProduct = existingProductsList.get(0);

//        System.out.println("Updating product with ID: " + id);
        if (file != null && !file.isEmpty()) {
//            System.out.println("File provided: " + file.getOriginalFilename());
            String contentType = file.getContentType();
//            System.out.println("Content type: " + contentType);
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new HttpMediaTypeNotSupportedException("Only image files are supported.");
            }

            String filePath = fileStorageService.storeFile(file);
            products.setImageUrl(filePath); // Update image URL
        } else {
            // Preserve existing image URL
            products.setImageUrl(existingProduct.getImageUrl());
//            System.out.println("No file provided or file is empty. Keeping existing image URL.");
        }

        // Merge the new data with the existing product
        existingProduct.setCategoryId(products.getCategoryId());
        existingProduct.setTitle(products.getTitle());
        existingProduct.setAuthor(products.getAuthor());
        existingProduct.setIsbn(products.getIsbn());
        existingProduct.setDescription(products.getDescription());
        existingProduct.setPrice(products.getPrice());
        existingProduct.setPublisher(products.getPublisher());
        existingProduct.setPublicationDate(products.getPublicationDate());
        existingProduct.setLanguage(products.getLanguage());
        existingProduct.setPages(products.getPages());
        existingProduct.setStockQuantity(products.getStockQuantity());
        existingProduct.setImageUrl(products.getImageUrl()); // Update image URL
        existingProduct.setStatus(products.getStatus());
        existingProduct.setUserId(products.getUserId());

        productsService.updateProduct(id, existingProduct);
        return redirectPath;
    }
}