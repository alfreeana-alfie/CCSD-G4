/*
    Filename: CategoriesRepository.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
    Description: Repository for Categories
 */

package com.ccsd.project.repository;

import com.ccsd.project.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
