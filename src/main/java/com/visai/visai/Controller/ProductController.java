package com.visai.visai.Controller;

import com.visai.visai.Entity.Product;
import com.visai.visai.Repository.ProductRepository;
import com.visai.visai.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // Endpoint to fetch all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Endpoint to fetch a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) {
        Optional<Product> product = productService.getProductDetails(productId);
        return product
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to create a new product
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product p) {
        try {
            // Save the product to the database
            Product savedProduct = productRepository.save(p);

            // Return success response with the saved product
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            // In case of an error, return a bad request response
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            // Product found, proceed to delete
            productRepository.deleteById(productId);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            // If product not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int productId, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(productId, productDetails);

        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); // Return the updated product
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the product was not found
        }
    }
}
