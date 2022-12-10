package com.vskkkkkshablon.controller;

import com.vskkkkkshablon.entities.Categories;
import com.vskkkkkshablon.entities.Products;
import com.vskkkkkshablon.entities.Users;
import com.vskkkkkshablon.service.ProductService;
import com.vskkkkkshablon.service.UserService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainContrloller {
  @Autowired
  private ProductService productService;

  @Autowired
  private UserService userService;

  @Value("${file.avatar.viewPath}")
  private String viewPath;

  @Value("${file.avatar.uploadPath}")
  private String uploadPath;

  @Value("${file.avatar.defaultPicture}")
  private String defaultPicture;

  /**
   * Gets products and categories lists.
   *
   * @param model for transfering model attribte
   *              to page
   * @return page from resources
   */
  @GetMapping("/")
  public String index(Model model) {

    model.addAttribute("currentUser", getUserData());

    List<Products> products = productService.getAllProducts();
    model.addAttribute("tovary", products);

    List<Categories> categories = productService.getAllCategories();
    model.addAttribute("categories", categories);

    return "index";
  }

  /**
   * Gets list of products.
   *
   * @param model for attribute
   * @return page
   */
  @GetMapping("/products")
  public String products(Model model) {

    model.addAttribute("currentUser", getUserData());

    List<Products> products = productService.getAllProducts();
    model.addAttribute("tovary", products);

    return "products";
  }

  /**
   * Edit page for products.
   *
   * @param model get item and categories for check
   * @param id    get item
   * @return page details
   */
  @GetMapping(value = "/details")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
  public String edit(Model model, @RequestParam(name = "id") Long id) {
    model.addAttribute("currentUser", getUserData());

    Products item = productService.getProduct(id);
    model.addAttribute("item", item);


    List<Categories> categories2 = productService.getAllCategories();
    model.addAttribute("categories2", categories2);

    return "details";
  }

  /**
   * Details page for products only for read.
   *
   * @param model get item and categories for check
   * @param id    get item
   * @return page details2
   */
  @GetMapping(value = "/details2")

  public String details(Model model, @RequestParam(name = "id") Long id) {
    model.addAttribute("currentUser", getUserData());

    Products item = productService.getProduct(id);
    model.addAttribute("item", item);

    List<Categories> categories2 = productService.getAllCategories();
    model.addAttribute("categories2", categories2);

    return "details2";
  }

  /**
   * Save updated item.
   *
   * @param categories  request param
   * @param id          request param
   * @param name        request param
   * @param price       request param
   * @param amount      request param
   * @param description request param
   * @return home page
   */
  @PostMapping("/saveitem")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
  public String save(@RequestParam(name = "category_id") Long categories,
                     @RequestParam(name = "id", defaultValue = "0") Long id,
                     @RequestParam(name = "name", defaultValue = "No name") String name,
                     @RequestParam(name = "price", defaultValue = "0") int price,
                     @RequestParam(name = "amount", defaultValue = "0") int amount,
                     @RequestParam(name = "description", defaultValue = "No description")
                         String description) {

    Products product = productService.getProduct(id);

    if (product != null) {


      product.setName(name);
      product.setPrice(price);
      product.setAmount(amount);
      product.setDescription(description);
      productService.addCategoriesToP(product, categories);

      productService.saveProduct(product);

    }
    return "redirect:/";

  }

  /**
   * Gets additem page.
   *
   * @param model for list of categories which check
   *              which category item belongs
   * @return page
   */
  @GetMapping("/additem")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
  public String addItemForm(Model model) {
    model.addAttribute("currentUser", getUserData());

    List<Categories> categories = productService.getAllCategories();
    model.addAttribute("categories", categories);

    return "additem";
  }

  /**
   * Add item method.
   *
   * @param categories  request param
   * @param name        request param
   * @param price       request param
   * @param amount      request param
   * @param description request param
   * @return home page
   */
  @PostMapping("/additem")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
  public String addProduct(@RequestParam(name = "category_id") Long categories,
                           @RequestParam(name = "name", defaultValue = "No name") String name,
                           @RequestParam(name = "price", defaultValue = "0") int price,
                           @RequestParam(name = "amount", defaultValue = "0") int amount,
                           @RequestParam(name = "description", defaultValue = "No description")
                               String description) {


    Products product = new Products();
    product.setName(name);
    product.setPrice(price);
    product.setAmount(amount);
    product.setDescription(description);
    productService.addCategoriesToP(product, categories);

    productService.addProduct(product);


    return "redirect:/";

  }

  /**
   * Delete method.
   *
   * @param id for deleting item
   * @return home page
   */
  @PostMapping("/deleteitem")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
  public String deleteItem(@RequestParam(name = "id") Long id) {

    Products product = productService.getProduct(id);
    if (product != null) {
      productService.deleteProduct(product);
    }
    return "redirect:/";
  }

  /**
   * 403 page for 'access denied' message.
   *
   * @param model for user data
   * @return 403 page
   */
  @GetMapping("/403")
  public String accessDenied(Model model) {
    model.addAttribute("currentUser", getUserData());
    return "403";
  }

  /**
   * Login page.
   *
   * @param model for user data
   * @return page
   */
  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("currentUser", getUserData());
    return "login";
  }

  /**
   * Register page.
   *
   * @param model for user data
   * @return page
   */
  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("currentUser", getUserData());

    return "register";
  }

  /**
   * Register method uses userService method
   * createUser() and check whether there is a user or not.
   *
   * @param name       request param
   * @param email      request param
   * @param password   request param
   * @param rePassword request param
   * @return register page with success message if everythink ok
   */
  @PostMapping(value = "/register")
  public String toRegister(@RequestParam(name = "user_full_name") String name,
                           @RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "re_user_password") String rePassword) {
    if (password.equals(rePassword)) {
      Users newUser = new Users();
      newUser.setFullName(name);
      newUser.setPassword(password);
      newUser.setEmail(email);

      if (userService.createUser(newUser) != null) {
        return "redirect:/register?success";
      }

    }
    return "redirect:/register?error";
  }

  /**
   * Profile page.
   *
   * @param model for user data
   * @return page
   */
  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public String profile(Model model) {
    model.addAttribute("currentUser", getUserData());
    return "profile";
  }

  @PostMapping("/uploadavatar")
  @PreAuthorize("isAuthenticated()")
  public String uploadAvatar(@RequestParam(name = "user_avatar") MultipartFile file) {

    try {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(uploadPath + "pic.jpg");
      Files.write(path, bytes);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:/profile";
  }

  /**
   * Authorize user from first line
   * second line check condition, if user's
   * not anonymous, then security user become
   * common user and then used method to get data of user.
   * This method is needed for get data of user for all pages.
   *
   * @return user data
   */
  private Users getUserData() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      User secUser = (User) authentication.getPrincipal();
      Users myUser = userService.getUserByEmail(secUser.getUsername());
      return myUser;
    }
    return null;
  }

}
