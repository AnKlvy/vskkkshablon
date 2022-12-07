package com.vskkkkkshablon.controller;

import com.vskkkkkshablon.entities.Categories;
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
  public String index(Model model) {

    List<Products> products = productService.getAllProducts();
    model.addAttribute("tovary", products);

    return "index";
  }

  @GetMapping("/additem")
  public String addItemForm(Model model) {
    List<Categories> categories =productService.getAllCategories();
    model.addAttribute("categories", categories);

    return "additem";
  }

  @PostMapping("/additem")
  public String addProduct(@RequestParam(name="category_id") Long categories,
                           @RequestParam(name = "name", defaultValue = "No name") String name,
                           @RequestParam(name = "price", defaultValue = "0") int price,
                           @RequestParam(name = "amount", defaultValue = "0") int amount,
                           @RequestParam(name = "description", defaultValue = "No description") String description) {


    Products product = new Products();
    product.setName(name);
    product.setPrice(price);
    product.setAmount(amount);
    product.setDescription(description);
    productService.addCategoriesToP(product,categories);

    productService.addProduct(product);


    return "redirect:/";

  }

  @PostMapping("/deleteitem")
  public String deleteItem(@RequestParam(name = "id") Long id) {

    Products product = productService.getProduct(id);
    if (product != null) {
      productService.deleteProduct(product);
    }
    return "redirect:/";
  }

 @GetMapping("/403")
  public String accessDenied(Model model){
    return "403";
 }

}
