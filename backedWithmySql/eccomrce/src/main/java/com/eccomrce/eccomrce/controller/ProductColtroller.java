package com.eccomrce.eccomrce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ProductColtroller {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductBYCategoryHandler(@RequestParam String category,@RequestParam List<String>color,@RequestParam List<String>size,@RequestParam Integer minPrice,@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,@RequestParam String sort,@RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        System.out.println(category +" " +  color+" " +size+" " + minPrice+" " + maxPrice+" " + minDiscount+" " + sort+" " + stock+" " + pageNumber+" " + pageSize);

        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);



        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

    @PostMapping("/product")
    public ResponseEntity<HashMap<String, List<Product>>> getProductByCategory(@RequestBody String[] category) {

        System.out.println("categorues = ======= " +  category );
        HashMap<String, List<Product>> res = new HashMap<>();
        for (String categor : category) {
            List<Product> products = productService.findbycategory(categor);
            res.put(categor, products != null ? products : new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }
    
    
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);

        return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
    }




    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String query) {
        List<Product> products = productService.searchProduct(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    


    

}
