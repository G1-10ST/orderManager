package com.mythik.orderManager.service;

import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
    List<Product> fetchAllProducts();

    Product addProduct(ProductRequest dto);

    Product updateProduct(Long id, ProductRequest dto);
}
