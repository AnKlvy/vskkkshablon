package com.vskkkkkshablon.service.impl;

import com.vskkkkkshablon.entities.Categories;
import com.vskkkkkshablon.entities.Products;
import com.vskkkkkshablon.repositories.CategoryRepository;
import com.vskkkkkshablon.repositories.ProductRepository;
import com.vskkkkkshablon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public Products addProduct(Products product) {
    return productRepository.save(product);
  }

  @Override
  public List<Products> getAllProducts() {
    return productRepository.findAllByAmountGreaterThanOrderByPriceDesc(0);
  }

  @Override
  public Products getProduct(Long id) {
    return productRepository.findByIdAndAmountGreaterThan(id, 0);
  }

  @Override
  public void deleteProduct(Products product) {
    productRepository.delete(product);
  }

  @Override
  public Products saveProduct(Products product) {
    return productRepository.save(product);
  }

  @Override
  public Products addCategoriesToP(Products s, Long c) {
    List<Categories> cates = new ArrayList<>();

    Categories category = categoryRepository.findById(c).get();
    cates.add(category);
    s.setCategories(cates);

    return s;
  }


  @Override
  public List<Categories> getAllCategories() {
    return categoryRepository.findAll();
  }


}
