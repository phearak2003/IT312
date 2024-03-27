package com.spring_boot.mart.online.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.mart.online.entity.Cart;
import com.spring_boot.mart.online.repository.CartRepository;
import com.spring_boot.mart.product.dto.ListProductDto;
import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.repository.ProductRepository;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/online")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class OnlineController {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @GetMapping("/list-product")
    public List<ListProductDto> products() {
        return productRepository.listProducts();
    }

    @PostMapping("/checkout")
    public void checkout(@RequestBody List<Cart> cart) {
        try {
            cartRepository.saveAll(cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/payment/{cartid}")
    public void payment(@PathVariable String cartid) {
        List<Cart> carts = cartRepository.getCartbycartID(cartid);
        if (!carts.isEmpty()) {
            Cart cart = carts.get(0);
            cart.setIsCheckout(1l); 
            cartRepository.save(cart);
        }
    }
}
