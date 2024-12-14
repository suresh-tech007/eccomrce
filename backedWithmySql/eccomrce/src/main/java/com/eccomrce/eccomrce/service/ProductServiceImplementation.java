package com.eccomrce.eccomrce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eccomrce.eccomrce.exception.ProductException;
import com.eccomrce.eccomrce.model.Category;
import com.eccomrce.eccomrce.model.Product;
import com.eccomrce.eccomrce.repository.CategoryRepository;
import com.eccomrce.eccomrce.repository.ProductRepository;
import com.eccomrce.eccomrce.request.CreateProduct;

@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("unused")
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProduct req) {

        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());

        if (topLevel == null) {
            Category topLavelCategory = new Category();

            topLavelCategory.setName(req.getTopLavelCategory());
            topLavelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLavelCategory);

        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory(), topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();

            secondLevelCategory.setName(req.getSecondLavelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);

        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLavelCategory(),
                secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();

            thirdLevelCategory.setName(req.getThirdLavelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);

        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        System.out.println("prodcts" + productId);
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);

        return "Product deleted Successfully";

    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product productt = findProductById(productId);
        
        productt.setTitle(req.getTitle());
        productt.setBrand( req.getBrand());
        productt.setCategory(req.getCategory());
        productt.setColor(req.getColor());
        productt.setDescription(req.getDescription());
        productt.setDiscountPercent(req.getDiscountPercent());
        productt.setDiscountedPrice(req.getDiscountedPrice());
        productt.setPrice(req.getPrice());
        productt.setQuantity(req.getQuantity());
        productt.setImageUrl(req.getImageUrl());
        productt.setSizes(req.getSizes());

        return productRepository.save(productt);

    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        
        Optional<Product> pro = productRepository.findById(id);

        
        if (pro.isPresent()) {
            return pro.get();
        }

        throw new ProductException("Product not found with id - " + id);
    }
    
    @Override
    public List<Product> findProductByCategoty(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageble = PageRequest.of(pageNumber, pageSize);

        List<Product> productss = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            productss = productss.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());

        }
        if (stock!=null) {
            if (stock.equals("in_stock")) {
                productss=productss.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
                
            }
            else if (stock.equals("out_of_stock")) {
                productss=productss.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
                
            }
            
        }

        int startIndex = (int) pageble.getOffset();

        int endIndex = Math.min(startIndex + pageble.getPageSize(), productss.size());

        List<Product> pageCotent = productss.subList(startIndex, endIndex);

        Page<Product> fileredProducts = new PageImpl<>(pageCotent,pageble,productss.size());

        return fileredProducts;
    }

    @Override
public List<Product> searchProduct(String query) {
    
    return productRepository.searchByName(query);
}

    @Override
    public List<Product> findAllProduct() {
        List<Product> producsts = productRepository.findAll();
        return producsts;
       
    }

    @Override
    public List<Product> findbycategory(String category) {
        List<Product> producsts = productRepository.findByCategory(category);
        return producsts;
    }


}
