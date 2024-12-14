package com.eccomrce.eccomrce.request;

 

 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 @Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class updateCartItemrequest {
    private int  quantity;
    private Long CartItemId;
}
