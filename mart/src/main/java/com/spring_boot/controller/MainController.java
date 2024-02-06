package com.spring_boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/login")
public class MainController {

    // @GetMapping("")
    // public String login() {
    //     return "login/login";
    // }

    // @PostMapping("/login/validation")
    // public ResponseEntity<?> saveProduct(@RequestBody Product product) {
    //     return productService.save(product);
    // }
}
