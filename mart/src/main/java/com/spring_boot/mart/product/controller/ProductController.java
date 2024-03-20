package com.spring_boot.mart.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring_boot.mart.product.entity.Payment;
import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.entity.Dashboard;
import com.spring_boot.mart.product.repository.ProductRepository;
import com.spring_boot.mart.product.repository.PaymentRepository;
import com.spring_boot.mart.product.service.PaymentService;
import com.spring_boot.mart.product.service.impl.ProductServiceImpl;

@Controller
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    PaymentService paymentService;

    @GetMapping("/payment")
    public String payment() {
        return "payment/index";
    }

    @GetMapping("/payment/test")
    public String testpayment() {
        return "payment/test";
    }

    @GetMapping("/payment/create")
    public String createPayment() {
        return "payment/invoice";
    }

    @PostMapping("/payment/save")
    public ResponseEntity<?> savePayment(@RequestBody List<Payment> products) {
        try {
            for (Payment payment : products) {
                productService.updateQuantity(payment.getQuantity(), payment.getProductName());
            }

            ResponseEntity<?> response = paymentService.save(products);

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save payment: " + e.getMessage());
        }
    }

    @GetMapping("")
    public String allProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Dashboard dashboards = new Dashboard();
        dashboards.setPaymentThisMonth(paymentRepository.paymentThisMonth());
        dashboards.setPaymentToday(paymentRepository.paymentToday());
        dashboards.setProductCount(productRepository.productCount());
        dashboards.setTotal(paymentRepository.totalThisMonth());
        model.addAttribute("dashboards", dashboards);
        return "product/dashboard";
    }

    @GetMapping("/soldproduct")
    public String allSoldProducts(Model model) {
        List<Payment> payments = paymentRepository.findAllByOrderByDateDesc();
        model.addAttribute("payments", payments);
        return "payment/soldproduct";
    }

    @GetMapping("/payment/recipe/{paymentid}")
    public String soldProductsRecipe(@PathVariable String paymentid, Model model) {
        List<Payment> payments = paymentRepository.getRecipe(paymentid);
        model.addAttribute("payments", payments);
        System.out.println(payments);
        return "payment/recipe";
    }

    @GetMapping("/soldproduct/detail/{id}")
    public String soldProductDetail(@PathVariable Long id, Model model) {
        Optional<Payment> payments = paymentRepository.findById(id);
        model.addAttribute("payments", payments);
        return "payment/detail";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Product> products = productRepository.findById(id);
        model.addAttribute("products", products);
        return "product/detail";
    }

    @GetMapping("/create")
    public String create() {
        return "product/create";
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@ModelAttribute Product product,
            @RequestParam("file") MultipartFile file) {
        return productService.save(product, file);
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Optional<Product> products = productRepository.findById(id);
        model.addAttribute("products", products);
        return "product/update";
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
            @ModelAttribute Product updatedProduct,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ResponseEntity<String> response = productService.update(id, updatedProduct, file);
        return response;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Optional<Product> products = productRepository.findById(id);
        model.addAttribute("products", products);
        return "product/delete";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }
}