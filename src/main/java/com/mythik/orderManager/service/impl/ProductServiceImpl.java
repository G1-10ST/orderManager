package com.mythik.orderManager.service.impl;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import com.mythik.orderManager.model.entity.Product;
import com.mythik.orderManager.model.request.ProductRequest;
import com.mythik.orderManager.repository.ProductRepository;
import com.mythik.orderManager.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(ProductRequest dto) {

        return productRepository.save(Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build()
        );
    }

    public Product updateProduct(Long id, ProductRequest dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());

        return productRepository.save(existing);
    }
}
