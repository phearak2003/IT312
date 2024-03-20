package com.spring_boot.mart.product.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_boot.mart.product.dto.ProductListDto;
import com.spring_boot.mart.product.repository.PaymentRepository;
import com.spring_boot.mart.product.repository.ProductRepository;

@RestController
@RequestMapping(path = "/api")
public class DropdownController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PaymentRepository paymentRepository;

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

    @GetMapping("/payment/thisyear")
    public List<Map<String, Object>> paymentThisYear() {
        List<Object[]> list = paymentRepository.paymentEveryMonth();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] objArray : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", objArray[0]);
            map.put("paymentCount", objArray[1]);
            map.put("totalSaleMoney", objArray[2]);
            result.add(map);
        }
        return result;
    }

    @GetMapping("/payment/bestproduct")
    public List<Map<String, Object>> popularProductThisMonth() {
        List<Object[]> list = paymentRepository.bestSaleThisMonth();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] objArray : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", objArray[0]);
            map.put("quantity", objArray[1]);
            result.add(map);
        }
        return result;
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

    @GetMapping("/product/barcode/{barcode}")
    public List<ProductListDto> productwithBarcode(@PathVariable String barcode) {
        List<Object[]> list = productRepository.getProductWithBarCode(barcode);
        List<ProductListDto> resultList = new ArrayList<>();
        for (Object[] data : list) {
            ProductListDto listDto = new ProductListDto();
            listDto.setUnitPrice((String) data[1]);
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