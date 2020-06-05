package com.geralt.securityapp.controller;

import com.geralt.securityapp.exception.NotFoundException;
import com.geralt.securityapp.exception.OutOfStockException;
import com.geralt.securityapp.model.Product;
import com.geralt.securityapp.model.Transaction;
import com.geralt.securityapp.model.User;
import com.geralt.securityapp.service.MyUserDetailService;
import com.geralt.securityapp.service.ProductService;
import com.geralt.securityapp.service.TransactionService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController implements ErrorController {

    @Autowired
    ProductService productService;

    @Autowired
    MyUserDetailService userService;

    @Autowired
    TransactionService transactionService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView("access-denied");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @RequestMapping("/profile/index")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile/index");
        List<Product> listProducts = productService.findAll();
        modelAndView.addObject("listProducts", listProducts);
        return modelAndView;
    }

    @RequestMapping("/admin/index")
    public ModelAndView productIndex() {
        ModelAndView modelAndView = new ModelAndView("admin/index");
        List<Product> listProducts = productService.findAll();
        modelAndView.addObject("listProducts", listProducts);
        return modelAndView;
    }

    @RequestMapping("/admin/save")
    public ModelAndView save(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/new-product");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/index");
        productService.save(product);
        return modelAndView;
    }

    @RequestMapping("/admin/update")
    public ModelAndView update(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/edit-product");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/index");
        productService.save(product);
        return modelAndView;
    }

//    @RequestMapping("/admin/delete/{productId}")
//    public ModelAndView delete(@PathVariable(name = "productId") int productId) {
//        ModelAndView modelAndView = new ModelAndView("redirect:/admin/index");
//        productService.delete(productId);
//        return modelAndView;
//    }

    @RequestMapping("/admin/edit/{productId}")
    public ModelAndView editProductForm(@PathVariable(name = "productId") int productId) {
        Optional<Product> product = productService.findById(productId);
        ModelAndView modelAndView;
        try {
            if(product.isEmpty()) {
                throw new NotFoundException("Product not found!!");
            }
            modelAndView = new ModelAndView("admin/edit-product");
            modelAndView.addObject("product", product);

        } catch (NotFoundException exception) {
            modelAndView = new ModelAndView("exception/not-found");
            return modelAndView;
        }

        return modelAndView;
    }

    @RequestMapping("/admin/new")
    public ModelAndView newProductForm() {
        ModelAndView modelAndView = new ModelAndView("admin/new-product");
        Product product = new Product();
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping("/profile/buy/{productId}")
    public ModelAndView buy(@PathVariable(name = "productId") int productId) {
        ModelAndView modelAndView;
        Transaction transaction = new Transaction();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userService.findByUserName(username);
        Product product = productService.getOne(productId);

        try {
            if(product.getStock() <= 0) {
                throw new OutOfStockException("Out of Stock");
            }
             modelAndView = new ModelAndView("redirect:/profile/index");
            transaction.setUser(user);
            transaction.setProduct(product);

            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            transaction.setTimestamp(timestamp);

            productService.decrementStock(product);

            transactionService.save(transaction);
        } catch (OutOfStockException exception) {
            modelAndView = new ModelAndView("redirect:/exception/outofstock");
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping("/profile/details/{productId}")
    public ModelAndView details(@PathVariable(name = "productId") int productId) {

        Product product = productService.getOne(productId);
        ModelAndView modelAndView;

            modelAndView = new ModelAndView("profile/details");
            modelAndView.addObject("product", product);

        return modelAndView;
    }

    @RequestMapping("/profile/history")
    public ModelAndView history() {
        ModelAndView modelAndView = new ModelAndView("profile/history");
        List<Object[]> productList = productService.getProducts();
        modelAndView.addObject("listProducts", productList);
        return modelAndView;
    }

    @Override
    @RequestMapping("/error")
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping("/exception/outofstock")
    public String outOfException() {
        return "exception/out-of-stock";
    }

}
