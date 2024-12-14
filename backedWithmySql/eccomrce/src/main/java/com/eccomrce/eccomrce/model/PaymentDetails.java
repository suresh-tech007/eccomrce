package com.eccomrce.eccomrce.model;

 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    //  @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private Long id;
    private String paymentMethod;
    private String status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReernceld;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
 


}
