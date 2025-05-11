package com.mythik.orderManager.controller;

import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.request.ProductRequest;
import com.mythik.orderManager.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> fetchProducts() {
        return ResponseEntity
                .status(OK)
                .body(productServiceImpl.fetchAllProducts());
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity
                .status(CREATED)
                .body(productServiceImpl.addProduct(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest dto) {

        Product product = productServiceImpl.updateProduct(id,dto);

        return ResponseEntity
                .status(OK)
                .body(product);
    }
}
