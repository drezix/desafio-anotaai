package com.drezix.desafio.services;

import com.drezix.desafio.domains.categories.Category;
import com.drezix.desafio.domains.categories.exceptions.CategoryNotFoundException;
import com.drezix.desafio.domains.products.Product;
import com.drezix.desafio.domains.products.ProductDTO;
import com.drezix.desafio.domains.products.exceptions.ProductNotFoundException;
import com.drezix.desafio.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private CategoryService categoryService;

    private ProductRepository repository;

    public ProductService(CategoryService categoryService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.repository = productRepository;
    }

    public Product insert(ProductDTO productData) {
        Category category = this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        return newProduct;
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .ifPresent(product::setCategory);
        }
        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);

        return product;
    }

    public void delete(String id) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(product);
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }


}
