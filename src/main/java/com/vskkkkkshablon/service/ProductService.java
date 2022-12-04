package com.vskkkkkshablon.service;

import com.vskkkkkshablon.entities.Categories;
import com.vskkkkkshablon.entities.Products;

import java.util.List;

public interface ProductService {
  Products addProduct(Products product);
  List<Products> getAllProducts();
  Products getProduct(Long id);
  void deleteProduct(Products product);
  Products saveProduct(Products product);
  Products addCategoriesToP(Products s, Long c);

  List<Categories> getAllCategories();
}
