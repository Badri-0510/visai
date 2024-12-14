package com.visai.visai.Service;

import com.visai.visai.Entity.Order;
import com.visai.visai.Entity.Product;
import com.visai.visai.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository=productRepository;
    }

    public Optional<Product> getProductDetails(int orderId) {
        return productRepository.findById(orderId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to update an existing product
    public Product updateProduct(int productId, Product productDetails) {
        // Find the existing product by ID
        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            // Update the fields
            product.setName(productDetails.getName());
            product.setBrand(productDetails.getBrand());
            product.setPrice(productDetails.getPrice());
            product.setMargin(productDetails.getMargin());
            product.setQuantity(productDetails.getQuantity());

            // Save the updated product
            return productRepository.save(product);
        } else {
            return null; // Return null if the product doesn't exist
        }
    }

}
