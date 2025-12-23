package api.ultimate.pay.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import api.ultimate.pay.RazorpayConfig;
import api.ultimate.pay.dto.OrderRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final RazorpayConfig razorpayConfig;

    public Order createOrder(OrderRequest request) throws Exception {
        RazorpayClient client = new RazorpayClient(
                razorpayConfig.getKeyId(),
                razorpayConfig.getKeySecret());

        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", request.getAmount() * 100);
        orderReq.put("currency", request.getCurrency());
        orderReq.put("receipt", request.getReceipt());

        return client.orders.create(orderReq);
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {
        try {
            String payload = orderId + "|" + paymentId;
            return com.razorpay.Utils.verifySignature(payload, signature, razorpayConfig.getKeySecret());
        } catch (Exception e) {
            return false;
        }
    }
}
