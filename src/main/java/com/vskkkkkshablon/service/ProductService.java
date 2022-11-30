package com.vskkkkkshablon.service;

import com.vskkkkkshablon.entities.Products;

import java.util.List;

public interface ProductService {
  Products addItem(Products items);
  List<Products> getAllItems();
  Products getItem(Long id);
  void deleteItem(Products item);
  Products saveItem(Products item);
}
