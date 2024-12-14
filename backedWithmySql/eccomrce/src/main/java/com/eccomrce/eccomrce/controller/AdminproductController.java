package com.eccomrce.eccomrce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.request.CreateProduct;
 
import com.eccomrce.eccomrce.response.ApiResponse;
import com.eccomrce.eccomrce.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/admin/products")
public class AdminproductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProduct req){

         productService.createProduct(req);
        ApiResponse res = new ApiResponse();
        res.setMessage("product created successfully");
        res.setStatus("success");

        return new ResponseEntity<>(res,HttpStatus.OK);
        // return new ResponseEntity<>(product,HttpStatus.CREATED);
        
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getMethodName(@RequestHeader("Authorization") String jwt) {

        List<Product> products = productService.findAllProduct();
        return new ResponseEntity<>(products,HttpStatus.ACCEPTED);
    }
    

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId,@RequestHeader("Authorization") String jwt) throws ProductException{

         productService.deleteProduct(productId);
    
        ApiResponse res = new ApiResponse();
        res.setMessage("product deleted successfully");
        res.setStatus("success");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    // @GetMapping("/all")
    // public ResponseEntity<List<Product>> allproducts() {
    //     List<Product> products = productService.findAllProduct();
    //     return new ResponseEntity<>(products,HttpStatus.OK);
    // }

    @PutMapping("/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId,@RequestBody Product req) throws ProductException{

        productService.updateProduct(productId,req);
        ApiResponse res = new ApiResponse();
        res.setMessage("product updated successfully");
        res.setStatus("success");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMultiple(@RequestBody CreateProduct[] req) {
       for (CreateProduct createProduct : req) {
        productService.createProduct(createProduct);
        
       }
       ApiResponse res = new ApiResponse();
       res.setMessage("products created successfully");
       res.setStatus("success");

       return new ResponseEntity<>(res,HttpStatus.CREATED);
    }
    
    

    
}
