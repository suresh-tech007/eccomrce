package com.eccomrce.eccomrce.request;

import java.util.HashSet;
import java.util.Set;

import com.eccomrce.eccomrce.model.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {

    private String title;
    private String description;

    private int price;

    private int discountedPrice ;
    private int discountPercent ;

    private int quantity;

    private String brand;

    private String color;

    private Set<Size> size = new HashSet<>();

    private String imageUrl;
    private String topLavelCategory;
    private String secondLavelCategory;
    private String thirdLavelCategory;
   
   


}
