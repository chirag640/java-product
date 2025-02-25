package com.product.services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {
	
		private static String CLIENT_ID = "Af0FIhZgHb_7hMqDr6mYTQ3FJmjpUfZQ8xrSVCdKeOGJXHJ5ib2QLxUp8CGKCXaY8D5YimiWQtnL7MHK";
		private static String CLIENT_SECRET = "ECRcLKxxuHjrQr4Mkij4kBpvZUIvoTQkS8Cvuo4OX-8fsLVqIq-s7iTjRAvqOZ6U5zam24ynEHPxzaJr";
	    private static  String MODE = "sandbox";

	    private static APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET);

		
		public static Payment createPayment(
		Double total,
		String currency,
		String Method,
		String intent,
		String description,
		String cancelUrl,
		String successUrl) throws PayPalRESTException{
			Amount amount =  new Amount();
			amount.setCurrency(currency);
			amount.setTotal(String.format(Locale.forLanguageTag(currency),"%.2f", total));
			
			Transaction transaction = new Transaction();
			transaction.setDescription(description);
			transaction.setAmount(amount);
			
			List<Transaction> transactions = new ArrayList<>();
			transactions.add(transaction);
			
			Payer payer = new Payer();
			payer.setPaymentMethod(Method);
			
			Payment payment = new Payment();
			payment.setIntent(intent);
			payment.setPayer(payer);
			payment.setTransactions(Arrays.asList(transaction));
			RedirectUrls redirectUrls= new RedirectUrls();
			redirectUrls.setCancelUrl(cancelUrl);
			redirectUrls.setReturnUrl(successUrl);
			payment.setRedirectUrls(redirectUrls);
			
			return payment.create(apiContext);
		}
		public static Payment excecutePayment(String paymentId,String payerId) throws PayPalRESTException{
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			return payment.execute(apiContext, paymentExecute);
		}
		public static APIContext getAPIContext() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
}