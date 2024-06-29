/*
    Filename: Products.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
 */
package com.ccsd.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Column(name = "category_id")
    int categoryId;
    String title;
    String author;
    String isbn;
    String description;
    @Column(columnDefinition = "DECIMAL(5,2)")
    BigDecimal price;
    String publisher;
    @Column(name = "publication_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate publicationDate;
    String language;
    int pages;
    @Column(name = "stock_quantity")
    int stockQuantity;
    @Column(name = "image_url")
    String imageUrl;
    String status = "A";
    @Column(name = "user_id")
    private int userId;

    public Products(int categoryId,
                    String title,
                    String author,
                    String isbn,
                    String description,
                    BigDecimal price,
                    String publisher,
                    LocalDate publicationDate,
                    String language,
                    int pages,
                    int stockQuantity,
                    String imageUrl,
                    int userId) {
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.language = language;
        this.pages = pages;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

}
