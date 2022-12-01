package com.vskkkkkshablon.controller;

import com.vskkkkkshablon.entities.Products;
import com.vskkkkkshablon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainContrloller {
  @Autowired
  private ProductService productService;

  @GetMapping("/")
  public String index(Model model){

    List<Products> products = productService.getAllProducts();
    model.addAttribute("tovary", products);

    return "index";
  }
}
