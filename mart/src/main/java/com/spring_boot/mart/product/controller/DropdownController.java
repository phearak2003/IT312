package com.spring_boot.mart.product.controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.mart.product.dto.ProductListDto;
import com.spring_boot.mart.product.repository.ProductRepository;

@RestController
@RequestMapping(path = "/api")
public class DropdownController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public List<ProductListDto> productList() {
        List<Object[]> list = productRepository.getAll();
        List<ProductListDto> resultList = new ArrayList<>();

        for (Object[] data : list) {
            ProductListDto listDto = new ProductListDto();
            listDto.setId((String) data[0]);
            listDto.setProductName((String) data[0]);
            resultList.add(listDto);
        }

        return resultList;
    }

    @GetMapping("/product/price/{product}")
    public List<ProductListDto> priceList(@PathVariable String product) {
        List<Object[]> list = productRepository.getPrice(product);
        List<ProductListDto> resultList = new ArrayList<>();

        for (Object[] data : list) {
            ProductListDto listDto = new ProductListDto();
            listDto.setId((String) data[0]);
            listDto.setProductName((String) data[0]);
            resultList.add(listDto);
        }
        return resultList;
    }

    @GetMapping("/product/quantity/{product}")
    public List<ProductListDto> quantityList(@PathVariable String product) {
        List<Object[]> list = productRepository.getQuantity(product);
        List<ProductListDto> resultList = new ArrayList<>();

        for (Object[] data : list) {
            ProductListDto listDto = new ProductListDto();
            listDto.setId(String.valueOf((Long) data[0]));
            resultList.add(listDto);
        }

        return resultList;
    }

}