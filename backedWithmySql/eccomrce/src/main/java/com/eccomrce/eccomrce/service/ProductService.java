package com.eccomrce.eccomrce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.request.CreateProduct;

public interface ProductService {
    
    public Product createProduct(CreateProduct Req );

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId , Product req) throws  ProductException;

    public  Product findProductById(Long id) throws ProductException;

    public List<Product>  findProductByCategoty(String category);
    public List<Product>  findbycategory(String category);

    public Page<Product> getAllProduct(String category , List<String> colors , List<String> sizes, Integer minPrice , Integer maxPrice , Integer minDiscount , String sort , String stock , Integer pageNumber , Integer pageSize  );


    public List<Product> searchProduct(String query);

    public List<Product> findAllProduct();

}
