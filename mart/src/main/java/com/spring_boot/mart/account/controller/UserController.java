package com.spring_boot.mart.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring_boot.mart.account.dto.MenuItemDto;
import com.spring_boot.mart.account.dto.UserDto;
import com.spring_boot.mart.account.entity.Permission;
import com.spring_boot.mart.account.entity.User;
import com.spring_boot.mart.account.repository.PermissionRepository;
import com.spring_boot.mart.account.repository.UserRepository;
import com.spring_boot.mart.account.service.impl.UserServiceImpl;
import com.spring_boot.mart.product.entity.Product;
import com.spring_boot.mart.product.repository.ProductRepository;
import com.spring_boot.mart.product.service.impl.ProductServiceImpl;

import jakarta.servlet.http.HttpSession;

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
    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> validation(@RequestBody UserDto user, HttpSession session) {
        User validation = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (validation != null) {
            if (validation.getIsActive() == 1) {
                return ResponseEntity.ok(validation.getRole());
            }
            return ResponseEntity.badRequest().body("User account has been blocked!");
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @GetMapping("/fetch-menu")
    public ResponseEntity<List<MenuItemDto>> fetchMenu(@RequestParam String role) {
        List<Permission> permissions = permissionRepository.findByRoleAndIsActive(role, 1);
        List<MenuItemDto> menuItems = new ArrayList<>();
        for (Permission permission : permissions) {
            MenuItemDto menuItem = new MenuItemDto();
            menuItem.setLabel(permission.getMenu());
            menuItem.setLink(permission.getLink());
            menuItem.setIcon(permission.getIcon());
            menuItems.add(menuItem);
        }
        return ResponseEntity.ok(menuItems);
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