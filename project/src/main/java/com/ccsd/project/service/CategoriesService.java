/*
    Filename: CategoriesService.java
    File Updated: 2024-06-24
    Edited by: Aidil Redza
    Description: Services to handle Categories Modules flows.
*/

package com.ccsd.project.service;

import com.ccsd.project.model.Categories;
import com.ccsd.project.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }
    /*
     * Description:
     *   Method: Get all Categories List [ADMIN].
     * */
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }
}