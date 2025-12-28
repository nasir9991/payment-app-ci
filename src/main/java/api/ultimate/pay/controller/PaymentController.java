package api.ultimate.pay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;

import api.ultimate.pay.dto.OrderRequest;
import api.ultimate.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class PaymentController {

	private final PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderRequest request) throws Exception {
    	System.out.println("testing >>>>>>");
        Order order = paymentService.createOrder(request);
        return order.toString();
    }

    @PostMapping("/verify")
    public boolean verifyPayment(
            @RequestParam String razorpay_order_id,
            @RequestParam String razorpay_payment_id,
            @RequestParam String razorpay_signature) {

        return paymentService.verifySignature(
                razorpay_order_id,
                razorpay_payment_id,
                razorpay_signature);
    }
}
