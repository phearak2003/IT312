package com.spring_boot.mart.account.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring_boot.mart.account.dto.UserDto;
import com.spring_boot.mart.account.entity.User;
import com.spring_boot.mart.account.repository.UserRepository;
import com.spring_boot.mart.account.service.impl.UserServiceImpl;
import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.repository.ProductRepository;
import com.spring_boot.mart.product.service.impl.ProductServiceImpl;

@Controller
@RequestMapping(path = "/account")
public class UserController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> validation(@RequestBody UserDto user) {
        User validation = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (validation != null) {
            if(validation.getIsActive() == 1){
                return ResponseEntity.ok("Login Successfully!");
            }
            return ResponseEntity.badRequest().body("User account has been block!");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/login/list";
    }

    @GetMapping("/user/detail/{username}")
    public String list(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "/login/detail";
    }

    @GetMapping("/user/delete/{username}")
    public String delete(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "/login/delete";
    }

    @PostMapping("/user/delete/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        return userService.deleteUser(username);
    }

    @GetMapping("/user/update/{username}")
    public String update(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "/login/update";
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/create")
    public String create() {
        return "/login/create";
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User user) {
        return userService.createUser(user);
    }
}