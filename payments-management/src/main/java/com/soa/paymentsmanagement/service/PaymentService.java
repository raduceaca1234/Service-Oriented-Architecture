package com.soa.paymentsmanagement.service;

import com.soa.paymentsmanagement.model.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
	
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
	public String charge(PaymentRequest chargeRequest) throws StripeException {
		 Map<String, Object> chargeParams = new HashMap<>();
	     chargeParams.put("amount", chargeRequest.getAmount());
	     chargeParams.put("currency", PaymentRequest.Currency.INR);
	     chargeParams.put("source", chargeRequest.getToken().getId());
	     
		Charge charge = Charge.create(chargeParams);
		return charge.getId();
	}
}
